import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @ViewChild('nmLogin') nmLogin: ElementRef;
  @ViewChild('nmSenha') nmSenha: ElementRef;

  loading: boolean = false;
  loadingMessage: string = "Autenticando..."

  loginResult: any = { success: true, message: '' };

  constructor(
    private _router: Router
  ) { }

  ngOnInit() {
    
  }

}
