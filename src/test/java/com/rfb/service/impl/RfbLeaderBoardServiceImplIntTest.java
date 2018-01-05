package com.rfb.service.impl;

import com.rfb.RfbloyaltyApp;
import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbEventAttendance;
import com.rfb.domain.RfbLeaderBoardView;
import com.rfb.domain.RfbLocation;
import com.rfb.repository.RfbEventAttendanceRepository;
import com.rfb.repository.RfbEventRepository;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.repository.UserRepository;
import com.rfb.service.RfbLeaderBoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RfbloyaltyApp.class})
public class RfbLeaderBoardServiceImplIntTest {

    @Autowired private RfbLeaderBoardService rfbLeaderBoardService;
    @Autowired private RfbLocationRepository rfbLocationRepository;
    @Autowired private RfbEventRepository rfbEventRepository;
    @Autowired private RfbEventAttendanceRepository rfbEventAttendanceRepository;
    @Autowired private UserRepository userRepository;

    private static final long JOHNNY_ID = 6;
    private static final long RUNNER_ID = 4;
    private static final long ORGANIZER_ID = 5;
    private static final long EVENT_LOCATION = 1;

    private RfbEvent organizerCreatesEvent(LocalDate eventDate) {
        RfbLocation rfbLocation = rfbLocationRepository.getOne(EVENT_LOCATION);
        RfbEvent rfbEvent = new RfbEvent().eventCode(UUID.randomUUID().toString())
                .eventDate(eventDate)
                .rfbLocation(rfbLocation);
        return rfbEventRepository.saveAndFlush(rfbEvent);
    }

    private RfbEventAttendance userRegistersForEvent(long userId, RfbEvent rfbEvent) {
        RfbEventAttendance rfbEventAttendance = new RfbEventAttendance()
                .user(userRepository.getOne(userId))
                .attendanceDate(rfbEvent.getEventDate())
                .rfbEvent(rfbEvent);
        return rfbEventAttendanceRepository.saveAndFlush(rfbEventAttendance);
    }

    @Test
    public void test() throws Exception {
        //Arrange
        LocalDate newEvent1Date = LocalDate.of(2017, 12, 28);
        LocalDate newEvent2Date = LocalDate.of(2017, 12, 29);
        RfbEvent newEvent1 = organizerCreatesEvent(newEvent1Date);
        RfbEvent newEvent2 = organizerCreatesEvent(newEvent2Date);
        userRegistersForEvent(JOHNNY_ID, newEvent1);
        userRegistersForEvent(JOHNNY_ID, newEvent2);
        userRegistersForEvent(RUNNER_ID, newEvent1);
        userRegistersForEvent(RUNNER_ID, newEvent2);
        userRegistersForEvent(ORGANIZER_ID, newEvent1);
        RfbLeaderBoardView johnnyExpected = new RfbLeaderBoardView(JOHNNY_ID, "johnny", 15).percent("100.0");
        RfbLeaderBoardView runnerExpected = new RfbLeaderBoardView(RUNNER_ID, "runner", 10).percent("66.66666666666666");

        //Act
        Collection<RfbLeaderBoardView> actual = rfbLeaderBoardService.getLeaderBoardViewForLocation(EVENT_LOCATION);

        //Assert
        assertEquals("Leader Board should have 2 entries", 2, actual.size());
        assertTrue("Johnny Leader Board record did not match " + johnnyExpected, actual.contains(johnnyExpected));
        assertTrue("Runner Leader Board record did not match " + runnerExpected, actual.contains(runnerExpected));
        Iterator<RfbLeaderBoardView> iterator = actual.iterator();
        RfbLeaderBoardView johnnyActual = iterator.next();
        RfbLeaderBoardView runnerActual = iterator.next();
        assertEquals("Leader Board entries are not in the correct order", johnnyExpected, johnnyActual);
        assertEquals("Second place entry was not found where it was expected", runnerExpected, runnerActual);
        assertEquals("Percentage was not formatted correctly", johnnyExpected.getPercent(), johnnyActual.getPercent());
        assertEquals("Percentage was not formatted correctly", runnerExpected.getPercent(), runnerActual.getPercent());
    }
}
