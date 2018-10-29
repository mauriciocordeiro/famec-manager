
import { mergeMap, map, filter } from 'rxjs/operators';
import { Component, ComponentFactoryResolver } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Http } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { ToastyService, ToastyConfig } from 'ng2-toasty';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { HotkeysService } from 'angular2-hotkeys';


import { ComponentManager, DialogUtils } from 'sol/utils';

import "rxjs/add/operator/map";
import "rxjs/add/operator/filter";
import "rxjs/add/operator/mergeMap";


@Component({
    selector: 'body',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private titleService: Title,

        // Dependencias do Quark
        private http: Http,
        private httpClient: HttpClient,

        // Dependencias do FormUtils
        private dialog: MatDialog,
        private toastyService:ToastyService,
        private toastyConfig: ToastyConfig,

        // Dependencias de componentes
        private componentFactoryResolver: ComponentFactoryResolver,

        //Dependencias de HotKeyManager
        private hotkeysService: HotkeysService
    ) {
        

    }

    async ngOnInit() {
    }

    ngAfterViewInit() {
        this.validateSession();
        this.routePageTitle();

       
    }

    validateSession(){
       
    }

    routePageTitle(){
        this.router.events.pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.activatedRoute),
        map(route => { while (route.firstChild) route = route.firstChild; return route; }),
        filter(route => route.outlet === 'primary'),
        mergeMap(route => route.data),)
        .subscribe((event) => this.titleService.setTitle(event['title']));
    }

}
