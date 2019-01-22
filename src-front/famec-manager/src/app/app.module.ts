import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule }    from '@angular/common/http';

import { MATERIAL_MODULES } from './app.material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidebarComponent } from './nav/sidebar/sidebar.component';
import { MenubarComponent } from './nav/menubar/menubar.component';
import { ContatoComponent } from './forms/contato/contato.component';
import { UsuarioComponent } from './forms/usuario/usuario.component';
import { LoadingComponent } from './nav/loading/loading.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    MenubarComponent,
    ContatoComponent,
    UsuarioComponent,
    LoadingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MATERIAL_MODULES,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
