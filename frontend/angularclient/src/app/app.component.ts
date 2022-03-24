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

  setNavActiveClass(a: string) {
    switch (a){
      case 'home':
        this.navHomeActiveClass = 'active'
        this.navVoteActiveClass = ''
        this.navStatisticsActiveClass = ''
        break;
      case 'vote':
        this.navVoteActiveClass = 'active'
        this.navHomeActiveClass = ''
        this.navStatisticsActiveClass = ''
        break;
      case 'statistics':
        this.navStatisticsActiveClass = 'active'
        this.navHomeActiveClass = ''
        this.navVoteActiveClass = ''
        break;
    }
  }
}
