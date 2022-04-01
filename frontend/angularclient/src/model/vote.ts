import {Image} from "./image";

export class Vote {
  id?: number | undefined;
  datetime: number | undefined;
  fk_ImageId_Winner: Image | undefined;
  fk_ImageId_Loser: Image | undefined;
  duration: number | undefined;
  winnerOnLeftSide: boolean | undefined;
}
