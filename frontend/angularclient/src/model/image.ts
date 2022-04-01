export class Image {
  id: number;
  datetime: bigint | undefined;
  catApiId: string | undefined;
  url: string | undefined;
  width: number | undefined;
  height: number | undefined;
  timesShown: number | undefined;
  catApiBreedid: string | undefined;

  constructor(id: number) {
    this.id = id;
  }
}
