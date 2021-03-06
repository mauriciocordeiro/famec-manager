import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit {
    /**
     * Loading screen to blur application whenever it is necessary
     */

    @Input('color') color = 'accent';
    @Input('mode')  mode  = 'indeterminate';
    @Input('value') value = 50;
    @Input('message') message;

    constructor() {}

    ngOnInit() {

    }
}
