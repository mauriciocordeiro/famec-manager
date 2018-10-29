import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToastyModule } from 'ng2-toasty';
import { DndModule } from 'ng2-dnd';
import { OverlayModule } from '@angular/cdk/overlay';
import { PortalModule } from '@angular/cdk/portal';
import { HotkeyModule } from 'angular2-hotkeys';
import { KzMaskDirective } from 'sol/components/form/elements/text/ks-mask.directive.ts';
import { AppComponent } from 'app/app.component';
import { MATERIAL_MODULES } from 'app/app.material.modules';
import { COMPONENTS_ARRAY } from 'app/app.components';
import { PROVIDERS } from 'app/app.providers';
import { appRoutes } from 'app/app.routers';
import { Md2DatepickerModule, MdNativeDateModule }  from 'md2';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';

import { registerLocaleData } from '@angular/common';
import localeBr from '@angular/common/locales/pt';

registerLocaleData(localeBr);

@NgModule({
  declarations: [
    COMPONENTS_ARRAY,
    KzMaskDirective
  ],
  exports: [
    RouterModule,
    MATERIAL_MODULES,
    BrowserModule,
    ToastyModule,
    BrowserAnimationsModule
  ],
  imports: [
    BrowserModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    OverlayModule,
    PortalModule,
    RouterModule.forChild(appRoutes),
    RouterModule.forRoot(appRoutes, { useHash: true }),
    ToastyModule.forRoot(),
    HotkeyModule.forRoot(),
    DndModule.forRoot(),
    Md2DatepickerModule,
    MdNativeDateModule,
    NgxMatSelectSearchModule,
    MATERIAL_MODULES
  ],
  providers:  PROVIDERS,
  bootstrap: [AppComponent],
  entryComponents: COMPONENTS_ARRAY
})

export class AppModule { }
