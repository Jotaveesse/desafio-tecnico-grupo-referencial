import { Component, input } from '@angular/core';
import { Resumo as ResumoModel } from '../../analise.models';

@Component({
  selector: 'app-resumo',
  templateUrl: './resumo.html',
})
export class Resumo {
  resumo = input.required<ResumoModel>();
}
