import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { RfbloyaltySharedModule, UserRouteAccessService } from './shared';
import { RfbloyaltyHomeModule } from './home/home.module';
import { RfbloyaltyAdminModule } from './admin/admin.module';
import { RfbloyaltyAccountModule } from './account/account.module';
import { RfbloyaltyEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';
import { LeaderBoardComponent } from './leader-board/leader-board.component';
import { LocationComponent } from './leader-board/location/location.component';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        RfbloyaltySharedModule,
        RfbloyaltyHomeModule,
        RfbloyaltyAdminModule,
        RfbloyaltyAccountModule,
        RfbloyaltyEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        LeaderBoardComponent,
        LocationComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class RfbloyaltyAppModule {}
