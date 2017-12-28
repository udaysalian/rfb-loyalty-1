import { TestBed, inject } from '@angular/core/testing';

import { RfbloyaltyLeaderBoardService } from './leader-board.service';

describe('LeaderBoardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RfbloyaltyLeaderBoardService]
    });
  });

  it('should be created', inject([RfbloyaltyLeaderBoardService], (service: RfbloyaltyLeaderBoardService) => {
    expect(service).toBeTruthy();
  }));
});
