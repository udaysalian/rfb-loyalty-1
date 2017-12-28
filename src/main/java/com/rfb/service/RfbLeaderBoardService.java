package com.rfb.service;

import com.rfb.domain.RfbLeaderBoardView;

import java.util.Collection;

public interface RfbLeaderBoardService {

    Collection<RfbLeaderBoardView> getLeaderBoardViewForLocation(Long locationId);
}
