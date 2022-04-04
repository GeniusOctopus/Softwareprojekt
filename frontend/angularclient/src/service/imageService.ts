import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ImageDetails} from "../model/imageDetails";

@Injectable()
export class ImageService {

  imageBaseUrl = "http://localhost:8080/image"

  constructor(private http: HttpClient) {

  }

  getImagesForVoting(): Observable<any> {
    return this.http.get(this.imageBaseUrl + "/voting");
  }

  async putIncreaseTimesShownForVoting(imageId: number) {
    return await this.http.put<number>(this.imageBaseUrl + `/increaseTimesShownForVoting/${imageId}`, imageId).toPromise();
  }

  async getDetails(imageId: number | undefined) {
    return await this.http.get<ImageDetails>(this.imageBaseUrl + `/details/` + imageId).toPromise();
  }
}
