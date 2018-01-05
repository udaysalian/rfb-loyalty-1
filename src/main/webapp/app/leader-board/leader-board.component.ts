import { Component, OnInit } from '@angular/core';
import { RfbLocation } from '../entities/rfb-location/rfb-location.model';
import { RfbLocationService } from '../entities/rfb-location/rfb-location.service';
import { DecimalPipe } from '@angular/common';

import { Principal, ResponseWrapper } from '../shared';
import { RfbloyaltyLocationPullDownComponent } from '../rfbloyalty-location-pull-down/rfbloyalty-location-pull-down.component';
import { RfbLeader } from './leader-board.model';
import { RfbloyaltyLeaderBoardService } from './leader-board.service';

import { JhiAlertService } from 'ng-jhipster';

// TODO when on the leader board and signing in the pull down does not get refreshed to the users home location
// Bug or feature?
@Component({
  selector: 'jhi-leader-board',
  templateUrl: './leader-board.component.html',
  styles: []
})
export class RfbloyaltyLeaderBoardComponent implements OnInit {

    account: any;
    locations: RfbLocation[];
    leaders: RfbLeader[];

    constructor(
        private principal: Principal,
        private locationService: RfbLocationService,
        private alertService: JhiAlertService,
        private leaderBoardService: RfbloyaltyLeaderBoardService
    ) { }

    ngOnInit() {
        // init the home location on the account so the console doesn't log a bunch of undefined errors while
        // waiting for the principal to load the account information, also automatically takes care of the use
        // cases where account is null or home location is not set on the account by defaulting the home location
        this.setDefaultHomeLocation();
        this.loadAccountInformation();
        this.loadLocations();
    }

    private loadAccountInformation() {
        this.principal.identity().then((account) => {
            if (account !== null && account.homeLocation !== null) {
                this.account = account;
            }
            this.loadLeaderBoardMembers(this.account.homeLocation);
        });
    }

    private setDefaultHomeLocation() {
        this.account = {
            homeLocation: 1
        }
    }

    loadLocations() {
        this.locationService.query({
            page: 0,
            size: 100,
            sort: ['locationName', 'ASC']}).subscribe(
            (res: ResponseWrapper) => {
                this.locations = res.json;
            },
            (res: ResponseWrapper) => { console.log(res) }
        );
    }

    private loadLeaderBoardMembers(locationId: number) {
        this.leaderBoardService.query(locationId)
            .subscribe(
                (res: ResponseWrapper) => this.leaders = res.json,
                () => this.noLeaderBoardFound());
    }

    private noLeaderBoardFound() {
        this.leaders = [];
        this.alertService.error('No events have been held for this location.');
    }

    onSelect(locationId: number) {
        this.loadLeaderBoardMembers(locationId);
    }
}
