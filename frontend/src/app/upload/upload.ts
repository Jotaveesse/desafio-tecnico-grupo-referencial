import { Component, inject, signal } from '@angular/core';
import { JsonPipe } from '@angular/common';
import { AnaliseService } from '../analise';

@Component({
  selector: 'app-upload',
  imports: [JsonPipe],
  templateUrl: './upload.html',
  styleUrl: './upload.css',
})
export class Upload {
  private readonly analiseService = inject(AnaliseService);

  arquivo = signal<File | null>(null);
  resposta = signal<any>(null);
  erro = signal<string | null>(null);

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.arquivo.set(input.files?.[0] ?? null);
    this.resposta.set(null);
    this.erro.set(null);
  }

  enviar() {
    const arquivo = this.arquivo();
    if (!arquivo) return;

    this.analiseService.enviar(arquivo).subscribe({
      next: (res) => this.resposta.set(res),
      error: (err) => this.erro.set(err.error?.mensagem ?? 'Não foi possível enviar o arquivo.'),
    });
  }
}
