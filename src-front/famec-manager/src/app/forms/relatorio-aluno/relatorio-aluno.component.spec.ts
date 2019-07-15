import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioAlunoComponent } from './relatorio-aluno.component';

describe('RelatorioAlunoComponent', () => {
  let component: RelatorioAlunoComponent;
  let fixture: ComponentFixture<RelatorioAlunoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatorioAlunoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatorioAlunoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
