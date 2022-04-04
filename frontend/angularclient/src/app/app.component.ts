import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  navHomeActiveClass = 'active'
  navVoteActiveClass = ''
  navStatisticsActiveClass = ''
  navRankingActiveClass = ''

  setNavActiveClass(a: string) {
    switch (a){
      case 'home':
        this.navHomeActiveClass = 'active'
        this.navVoteActiveClass = ''
        this.navStatisticsActiveClass = ''
        this.navRankingActiveClass = ''
        break;
      case 'vote':
        this.navVoteActiveClass = 'active'
        this.navHomeActiveClass = ''
        this.navStatisticsActiveClass = ''
        this.navRankingActiveClass = ''
        break;
      case 'statistics':
        this.navStatisticsActiveClass = 'active'
        this.navHomeActiveClass = ''
        this.navVoteActiveClass = ''
        this.navRankingActiveClass = ''
        break;
      case 'ranking':
        this.navStatisticsActiveClass = ''
        this.navHomeActiveClass = ''
        this.navVoteActiveClass = ''
        this.navRankingActiveClass = 'active'
        break;
    }
  }
}
