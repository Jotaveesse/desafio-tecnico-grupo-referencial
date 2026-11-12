import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LinhasInvalidas } from './linhas-invalidas';

describe('LinhasInvalidas', () => {
  let component: LinhasInvalidas;
  let fixture: ComponentFixture<LinhasInvalidas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LinhasInvalidas],
    }).compileComponents();

    fixture = TestBed.createComponent(LinhasInvalidas);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
