package com.rfb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rfb.domain.RfbLeaderBoardView;
import com.rfb.service.RfbLeaderBoardService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RfbLeaderBoardResource {

    private final Logger log = LoggerFactory.getLogger(RfbEventResource.class);

    private final RfbLeaderBoardService leaderBoardService;

    public RfbLeaderBoardResource(RfbLeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    /**
     * GET  /rfb-leaderBoard/:locationId get the members of the leader board for a given location id
     *
     * @param locationId the location to create a leader board view
     * @return the ResponseEntity with status 200 (OK) and with body of LeaderBoardView, or with status 404 (Not Found)
     * if the location id is invalid or there has never been a event at the given location
     */
    @GetMapping("/rfb-leaderBoard/{locationId}")
    @Timed
    public ResponseEntity<Collection<RfbLeaderBoardView>> getLeaderBoard(
            @PathVariable Long locationId) {
        log.debug("REST request to get RfbLeaderBoardView for location Id : {}", locationId);
        Collection<RfbLeaderBoardView> leaderBoardViews = leaderBoardService.getLeaderBoardViewForLocation(locationId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leaderBoardViews.isEmpty() ? null : leaderBoardViews));
    }
}
