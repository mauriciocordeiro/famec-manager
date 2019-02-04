import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContatoComponent } from './forms/contato/contato.component';
import { UsuarioComponent } from './forms/usuario/usuario.component';
import { FamiliaComponent } from './forms/familia/familia.component';

const routes: Routes = [
  //{ path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'familia', component: FamiliaComponent },
  { path: 'contato', component: ContatoComponent },
  { path: 'usuario', component: UsuarioComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
