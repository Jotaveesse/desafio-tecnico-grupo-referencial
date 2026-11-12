import { Component, input, output, signal } from '@angular/core';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.html',
  styleUrl: './upload.css',
})
export class Upload {
  carregando = input(false);
  arquivoEnviado = output<File>();

  arquivo = signal<File | null>(null);

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.arquivo.set(input.files?.[0] ?? null);
  }

  enviar() {
    const arquivo = this.arquivo();
    if (arquivo) this.arquivoEnviado.emit(arquivo);
  }
}
