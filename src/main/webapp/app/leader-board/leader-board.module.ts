import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfbloyaltySharedModule } from '../shared';
import { RfbloyaltyLeaderBoardService } from './leader-board.service';
import { RfbloyaltyLeaderBoardComponent } from './leader-board.component';
import { rfbLeaderBoardRoute } from './leader-board.routes';

const ENTITY_STATES = [
    ...rfbLeaderBoardRoute
];

@NgModule({
    imports: [
        RfbloyaltySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RfbloyaltyLeaderBoardComponent
    ],
    providers: [
        RfbloyaltyLeaderBoardService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfbloyaltyLeaderBoardModule { }
