import { Component, input } from '@angular/core';
import { LinhaInvalida } from '../../analise.models';

@Component({
  imports: [],
  selector: 'app-linhas-invalidas',
  styleUrl: './linhas-invalidas.css',
  templateUrl: './linhas-invalidas.html',
})
export class LinhasInvalidas {
  linhas = input.required<LinhaInvalida[]>();
}
