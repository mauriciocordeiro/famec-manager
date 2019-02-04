import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

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
import { FamiliaComponent } from './forms/familia/familia.component';
import { AlunoComponent } from './forms/aluno/aluno.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    MenubarComponent,
    ContatoComponent,
    UsuarioComponent,
    LoadingComponent,
    FamiliaComponent,
    AlunoComponent
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
  providers: [
    { provide: LOCALE_ID, useValue: 'pt' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
