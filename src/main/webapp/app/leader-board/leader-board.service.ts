import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../app.constants';

import { ResponseWrapper } from '../shared';
import { RfbLeader } from './leader-board.model';

@Injectable()
export class RfbloyaltyLeaderBoardService {

    private resourceUrl = SERVER_API_URL + 'api/rfb-leaderBoard';

    constructor(private http: Http) { }

    query(locationId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${locationId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = this.convertToRfbLeader(res.json());
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertToRfbLeader(json: any[]): RfbLeader[] {
        const rfbLeaders: RfbLeader[] = [];
        json.forEach(function(leader) {
            const miles = 0.621371 * leader.distance;
            rfbLeaders.push(new RfbLeader(leader.userId, leader.name, leader.percent, leader.distance, miles));
        });
        return rfbLeaders;
    }
}
