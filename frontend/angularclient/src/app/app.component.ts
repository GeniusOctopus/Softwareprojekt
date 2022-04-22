import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  navVoteActiveClass = ''
  navStatisticsActiveClass = ''
  navRankingActiveClass = ''

  setNavActiveClass(a: string) {
    switch (a){
      case 'vote':
        this.navVoteActiveClass = 'active'
        this.navStatisticsActiveClass = ''
        this.navRankingActiveClass = ''
        break;
      case 'statistics':
        this.navStatisticsActiveClass = 'active'
        this.navVoteActiveClass = ''
        this.navRankingActiveClass = ''
        break;
      case 'ranking':
        this.navStatisticsActiveClass = ''
        this.navVoteActiveClass = ''
        this.navRankingActiveClass = 'active'
        break;
    }
  }
}
