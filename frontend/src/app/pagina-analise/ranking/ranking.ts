import { Component, input } from '@angular/core';
import { RankingVeiculo } from '../../analise.models';

@Component({
  imports: [],
  selector: 'app-ranking',
  styleUrl: './ranking.css',
  templateUrl: './ranking.html',
})
export class Ranking {
  ranking = input.required<RankingVeiculo[]>();
}
