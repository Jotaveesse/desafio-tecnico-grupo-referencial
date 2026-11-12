import { Component, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AnaliseService } from '../analise';
import { AnaliseResposta } from '../analise.models';
import { Upload } from '../upload/upload';
import { Resumo } from './resumo/resumo';
import { Ranking } from './ranking/ranking';
import { Episodios } from './episodios/episodios';
import { LinhasInvalidas } from './linhas-invalidas/linhas-invalidas';

@Component({
  selector: 'app-pagina-analise',
  imports: [Upload, Resumo, Ranking, Episodios, LinhasInvalidas],
  templateUrl: './pagina-analise.html',
})
export class PaginaAnalise {
  private readonly service = inject(AnaliseService);

  resposta = signal<AnaliseResposta | null>(null);
  erro = signal<string | null>(null);
  carregando = signal(false);

  analisar(arquivo: File) {
    this.carregando.set(true);
    this.erro.set(null);
    this.resposta.set(null);

    this.service.enviar(arquivo).subscribe({
      next: (res) => {
        this.resposta.set(res);
        this.carregando.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.carregando.set(false);
        this.erro.set(
          err.status === 0
            ? 'Não foi possível conectar ao servidor.'
            : (err.error?.mensagem ?? 'Erro ao processar o arquivo.'),
        );
      },
    });
  }
}
