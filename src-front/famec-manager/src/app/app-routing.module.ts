import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContatoComponent } from './forms/contato/contato.component';
import { UsuarioComponent } from './forms/usuario/usuario.component';
import { FamiliaComponent } from './forms/familia/familia.component';
import { LoginComponent } from './forms/login/login.component';
import { RelatorioAlunoComponent } from './forms/relatorio-aluno/relatorio-aluno.component';

const routes: Routes = [
  //{ path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'familia', component: FamiliaComponent },
  { path: 'contato', component: ContatoComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'login', component: LoginComponent },
  { path: 'relatorio-aluno', component: RelatorioAlunoComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
