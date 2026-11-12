import { Component, input } from '@angular/core';
import { Resumo as ResumoModel } from '../../analise.models';

@Component({
  imports: [],
  selector: 'app-resumo',
  styleUrl: './resumo.css',
  templateUrl: './resumo.html',
})
export class Resumo {
  resumo = input.required<ResumoModel>();
}
