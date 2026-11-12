import { Component, computed, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Episodio, Gravidade } from '../../analise.models';

@Component({
  selector: 'app-episodios',
  imports: [DatePipe],
  styleUrl: './episodios.css',
  templateUrl: './episodios.html',
})
export class Episodios {
  episodios = input.required<Episodio[]>();
  filtro = signal<Gravidade | 'TODAS'>('TODAS');

  filtrados = computed(() => {
    const filtro = this.filtro();
    return filtro === 'TODAS'
      ? this.episodios()
      : this.episodios().filter((e) => e.gravidade === filtro);
  });

  onFiltroChange(event: Event) {
    this.filtro.set((event.target as HTMLSelectElement).value as Gravidade | 'TODAS');
  }
}
