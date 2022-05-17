export class Image {
  id: number;
  datetime: number | undefined;
  catApiId: string | undefined;
  url: string | undefined;
  width: number | undefined;
  height: number | undefined;
  timesShown: number | undefined;
  catApiBreedId: string | undefined;
  imageAsBase64String: string | undefined;

  constructor(id: number) {
    this.id = id;
  }
}
