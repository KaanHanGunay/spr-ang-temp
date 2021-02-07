import { IPerson } from 'app/shared/model/person.model';

export interface IPastCities {
  id?: number;
  city?: string;
  startYear?: number;
  endYear?: number;
  person?: IPerson;
}

export class PastCities implements IPastCities {
  constructor(public id?: number, public city?: string, public startYear?: number, public endYear?: number, public person?: IPerson) {}
}
