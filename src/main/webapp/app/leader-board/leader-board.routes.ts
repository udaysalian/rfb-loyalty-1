import { Routes } from '@angular/router';

import { RfbloyaltyLeaderBoardComponent } from './leader-board.component';

export const rfbLeaderBoardRoute: Routes = [
    {
    path: 'leader-board',
    component: RfbloyaltyLeaderBoardComponent,
    data: {
        pageTitle: 'Running For Brews Leader Board'
    }
}
];
