import { Component } from '@angular/core';
import { PaginaAnalise } from './pagina-analise/pagina-analise';

@Component({
  selector: 'app-root',
  imports: [PaginaAnalise],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}
