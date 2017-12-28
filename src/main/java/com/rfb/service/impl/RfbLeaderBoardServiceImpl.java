package com.rfb.service.impl;

import com.google.common.collect.ImmutableList;
import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbEventAttendance;
import com.rfb.domain.RfbLeaderBoardView;
import com.rfb.domain.RfbLocation;
import com.rfb.domain.User;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.service.RfbLeaderBoardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

@Service
@Transactional
public class RfbLeaderBoardServiceImpl implements RfbLeaderBoardService {

    private final int leaderBoardSize;
    private final long eventDistance;
    private final RfbLocationRepository rfbLocationRepository;
    private final DecimalFormat percentFormat;
    public RfbLeaderBoardServiceImpl(
            @Value("${spring.application.leaderBoardSize}") int leaderBoardSize,
            @Value("${spring.application.eventDistance}") long eventDistance,
            RfbLocationRepository rfbLocationRepository) {
        this.leaderBoardSize = leaderBoardSize;
        this.eventDistance = eventDistance;
        this.rfbLocationRepository = rfbLocationRepository;
        this.percentFormat = new DecimalFormat("0%");
        percentFormat.setMinimumFractionDigits(0);
        percentFormat.setMaximumFractionDigits(0);
        percentFormat.setDecimalSeparatorAlwaysShown(false);
    }

    @Override
    public Collection<RfbLeaderBoardView> getLeaderBoardViewForLocation(Long locationId) {
        List<RfbLocation> rfbLocations = rfbLocationRepository.findAll(ImmutableList.of(locationId));
        Collection<RfbLeaderBoardView> leaderBoardRecords = createLeaderBoardRecords(rfbLocations);
        return findLeaders(leaderBoardRecords);
    }

    private Collection<RfbLeaderBoardView> createLeaderBoardRecords(List<RfbLocation> rfbLocations) {
        Map<Long, RfbLeaderBoardView> map = new HashMap<>();
        for (RfbLocation rfbLocation : rfbLocations) {
            Collection<RfbEvent> rfbEvents = rfbLocation.getRvbEvents();
            for (RfbEvent rfbEvent : rfbEvents) {
                for (RfbEventAttendance attendance : rfbEvent.getRfbEventAttendances()) {
                    User user = attendance.getUser();
                    long userId = user.getId();
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    RfbLeaderBoardView view = new RfbLeaderBoardView(userId,
                            getName(firstName, lastName),
                            eventDistance);
                    if (!map.containsKey(userId)) {
                        map.put(userId, view);
                    } else {
                        map.get(userId).incrementDistance(eventDistance);
                    }
                }
            }
        }
        return map.values();
    }

    private String getName(String firstName, String lastName) {
        String name;
        if (null == firstName && null == lastName) {
            name = "First-Unk Last-Unk";
        } else if (null == lastName) {
            name = firstName;
        } else if (null == firstName) {
            name = lastName;
        } else {
            name = String.format("%s %s", firstName, lastName);
        }
        return name;
    }

    private Collection<RfbLeaderBoardView> findLeaders(Collection<RfbLeaderBoardView> leaderBoardRecords) {
        NavigableSet<RfbLeaderBoardView> sortedSet = new TreeSet<>(leaderBoardRecords);
        List<RfbLeaderBoardView> leaders = new ArrayList<>();
        if (!sortedSet.isEmpty()) {
            RfbLeaderBoardView leader = sortedSet.last();
            sortedSet.descendingSet().stream().filter(view -> leaders.size() < leaderBoardSize).forEach(view -> {
                view.setPercent(leader.getDistance(), percentFormat);
                leaders.add(view);
            });
        }
        return leaders;
    }
}
