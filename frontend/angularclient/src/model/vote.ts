import {Image} from "./image";

export class Vote {
  id: number | undefined;
  datetime: bigint | undefined;
  imageIdWinner: Image | undefined;
  imageIdLoser: Image | undefined;
  duration: number | undefined;
  winnerOnLeftSide: boolean | undefined;
}
