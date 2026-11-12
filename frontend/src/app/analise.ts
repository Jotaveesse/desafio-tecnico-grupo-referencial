import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { AnaliseResposta } from './analise.models';

@Service()
export class AnaliseService {
  private readonly http = inject(HttpClient);
  private readonly url = 'http://localhost:8080/api/analises';

  enviar(arquivo: File): Observable<AnaliseResposta> {
    const formData = new FormData();
    formData.append('arquivoCSV', arquivo);
    return this.http.post<AnaliseResposta>(this.url, formData);
  }
}
