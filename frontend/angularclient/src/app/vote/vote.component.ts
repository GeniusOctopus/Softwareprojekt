import { Component, OnInit } from '@angular/core';
import {Image} from "../../model/image";
import {ImageService} from "../../service/imageService";
import {Vote} from "../../model/vote";
import {VoteService} from "../../service/voteService";

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css']
})
export class VoteComponent implements OnInit {

  currentImages: Image[] = [];
  preloadedImages: Image[] = [];
  isLoading: boolean = false;
  startTime: number = 0;
  endTime: number = 0;
  overlayClass = "";
  hideLeftLike = "visually-hidden";
  hideRightLike = "visually-hidden";
  likeAnimation = "";

  constructor(private imageService: ImageService, private voteService: VoteService) {}

  async showImagesForVoting() {
    this.overlayClass = "overlay-milkglass";
    this.isLoading = true;
    await new Promise(f => setTimeout(f, 2000));
    this.imageService.getImagesForVoting()
      .subscribe(
        (response) => {
          this.currentImages = response;
          this.imageService.getImageString(this.currentImages[0].id).subscribe((response) => {this.currentImages[0].imageAsBase64String = "data:image/jpeg;base64," + response});
          this.imageService.getImageString(this.currentImages[1].id).subscribe((response) => {this.currentImages[1].imageAsBase64String = "data:image/jpeg;base64," + response});
        }
      );
    await this.preloadNextImages();
    this.isLoading = false;
    this.overlayClass = "";
    this.startTime = new Date().getTime();
  }

  async preloadNextImages() {
    this.imageService.getImagesForVoting()
      .subscribe(
        (response) => {
          this.preloadedImages = response;
          this.imageService.getImageString(this.preloadedImages[0].id).subscribe((response) => {this.preloadedImages[0].imageAsBase64String = "data:image/jpeg;base64," + response});
          this.imageService.getImageString(this.preloadedImages[1].id).subscribe((response) => {this.preloadedImages[1].imageAsBase64String = "data:image/jpeg;base64," + response});
        }
      );
  }

  async submitVote(imageWinner: Image, imageLoser: Image, winnerOnLeftSide: boolean) {

    if (winnerOnLeftSide)
      this.hideLeftLike = "";
    else
      this.hideRightLike = "";
    this.likeAnimation = "like-animation";
    await new Promise(f => setTimeout(f, 1200));
    this.likeAnimation = "";
    if (winnerOnLeftSide)
      this.hideLeftLike = "visually-hidden";
    else
      this.hideRightLike = "visually-hidden";

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

    this.currentImages = this.preloadedImages;

    await this.preloadNextImages();
  }

  ngOnInit(): void {
    this.showImagesForVoting()
  }
}
