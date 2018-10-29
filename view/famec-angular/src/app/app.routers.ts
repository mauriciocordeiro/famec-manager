import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { COMPONENTS } from './app.components';

const appName:string = "e-Trânsito";
export const appRoutes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: COMPONENTS['LoginComponent'], data: {title: 'Login - ' + appName}},
    { path: 'home', component: COMPONENTS['MainComponent'], data: {title: 'Inicial - ' + appName}}
];
