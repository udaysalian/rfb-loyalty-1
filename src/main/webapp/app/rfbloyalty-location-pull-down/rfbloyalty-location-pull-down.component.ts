import { Component, Input } from '@angular/core';
import { RfbLocation } from '../entities/rfb-location/rfb-location.model';

@Component({
  selector: 'jhi-rfbloyalty-location-pull-down',
  templateUrl: './rfbloyalty-location-pull-down.component.html',
  styles: []
})
export class RfbloyaltyLocationPullDownComponent {

    @Input() pullDownLabel: string;
    @Input() account: any;
    @Input() locations: RfbLocation[];
    @Input() required: boolean;

}
