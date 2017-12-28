import { Component, OnInit } from '@angular/core';
import { RfbLocation } from '../entities/rfb-location/rfb-location.model';
import { RfbLocationService } from '../entities/rfb-location/rfb-location.service';

import { Principal, ResponseWrapper } from '../shared';

@Component({
  selector: 'jhi-leader-board',
  templateUrl: './leader-board.component.html',
  styles: []
})
export class RfbloyaltyLeaderBoardComponent implements OnInit {

    settingsAccount: any;
    locations: RfbLocation[];

    constructor(
        private principal: Principal,
        private locationService: RfbLocationService
    ) { }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.settingsAccount = this.copyAccount(account);
        });
        this.loadLocations();
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl,
            homeLocation: account.homeLocation
        };
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
}
