import { IPerson } from 'app/shared/model/person.model';

export interface IPhone {
  id?: number;
  phoneNumber?: string;
  startYear?: number;
  endYear?: number;
  person?: IPerson;
}

export class Phone implements IPhone {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public startYear?: number,
    public endYear?: number,
    public person?: IPerson
  ) {}
}
