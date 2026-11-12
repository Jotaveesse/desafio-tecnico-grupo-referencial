import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaginaAnalise } from './pagina-analise';

describe('PaginaAnalise', () => {
  let component: PaginaAnalise;
  let fixture: ComponentFixture<PaginaAnalise>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaAnalise],
    }).compileComponents();

    fixture = TestBed.createComponent(PaginaAnalise);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
