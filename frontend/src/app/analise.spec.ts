import { TestBed } from '@angular/core/testing';
import { Analise } from './analise';

describe('Analise', () => {
  let service: Analise;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Analise);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
