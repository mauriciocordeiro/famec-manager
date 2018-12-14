import { Component, OnInit } from '@angular/core';
import { FamiliaService } from '../model/familia/familia.service';
import { UsuarioService } from '../model/usuario/usuario.service';
import { ResultSetMap } from '../utils/ResultSetMap';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

	constructor(
		public familaService:FamiliaService,
		public usuarioService:UsuarioService) { }

	ngOnInit() {
		// debugger;
		// this.familaService.test().forEach((str:String[]) => {
		// 	console.log(str);
		// 	return;
		// });

		
		this.usuarioService.getAll().forEach((element:ResultSetMap[]) => {
			console.log(element);
			return;
		});
	}
}
