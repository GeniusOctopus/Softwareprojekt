import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {VoteComponent} from "./vote/vote.component";
import {StatisticsComponent} from "./statistics/statistics.component";
import {RankingComponent} from "./ranking/ranking.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'vote'},
  {path: 'vote', component: VoteComponent},
  {path: 'statistics', component: StatisticsComponent},
  {path: 'ranking', component: RankingComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
