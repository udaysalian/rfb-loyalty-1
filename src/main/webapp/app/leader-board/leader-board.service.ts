import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../app.constants';

import { ResponseWrapper } from '../shared';

@Injectable()
export class RfbloyaltyLeaderBoardService {

    private resourceUrl = SERVER_API_URL + 'api/rfb-leaderBoard';

    constructor(private http: Http) { }

    query(locationId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${locationId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
