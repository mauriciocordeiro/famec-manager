import { Component, OnInit } from '@angular/core';
import { FamiliaService } from '../model/familia.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

	constructor(public familaService:FamiliaService) { }

	ngOnInit() {
		debugger;
		this.familaService.test().forEach((str:String[]) => {
			console.log(str);
		});
	}

}
