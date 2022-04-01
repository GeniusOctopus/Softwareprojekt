import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Vote} from "../model/vote";
import {catchError} from "rxjs/operators";
import {Observable} from "rxjs";
import {HttpError} from "./exception/HttpError";

@Injectable()
export class VoteService {

  voteBaseUrl = "http://localhost:8080/vote";

  constructor(private http: HttpClient) {}

  async postVote(vote: Vote): Promise<Observable<Vote>> {
    console.log("Image vote")
    console.log(`winner id: ${vote.fk_ImageId_Winner?.id}`)
    console.log(`winner url: ${vote.fk_ImageId_Winner?.url}`)
    console.log(`loser id: ${vote.fk_ImageId_Loser?.id}`)
    console.log(`loser url: ${vote.fk_ImageId_Loser?.url}`)
    console.log(`duration: ${vote.duration}`)

    let response: any;
    await this.http.post<Vote>(this.voteBaseUrl + "/add", vote).toPromise().then(data => {
      response = data
    });
    return response;
  }
}
