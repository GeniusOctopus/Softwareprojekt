import { Component, OnInit } from '@angular/core';
import {Image} from "../../model/image";
import {ImageService} from "../../service/imageService";
import {Vote} from "../../model/vote";
import {timestamp} from "rxjs";
import {VoteService} from "../../service/voteService";

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css']
})
export class VoteComponent implements OnInit {

  images: Image[] = [];
  isLoading: boolean = false;
  startTime: number = 0;
  endTime: number = 0;

  constructor(private imageService: ImageService, private voteService: VoteService) {}

  async showImagesForVoting() {
    this.isLoading = true;
    await new Promise(f => setTimeout(f, 2000));
    this.imageService.getImagesForVoting()
      .subscribe(
        (response) => {
          this.images = response;
        }
      );
    this.isLoading = false;
    this.startTime = new Date().getTime();
  }

  async submitVote(imageWinner: Image, imageLoser: Image, winnerOnLeftSide: boolean) {
    this.endTime = new Date().getTime();

    let vote: Vote = {
      datetime: new Date().getTime(),
      duration: this.endTime - this.startTime,
      fk_ImageId_Winner: imageWinner,
      fk_ImageId_Loser: imageLoser,
      winnerOnLeftSide: winnerOnLeftSide,
    }

    await this.imageService.putIncreaseTimesShownForVoting(imageWinner.id);
    await this.imageService.putIncreaseTimesShownForVoting(imageLoser.id);
    await this.voteService.postVote(vote)
  }

  ngOnInit(): void {
    this.showImagesForVoting()
  }
}
