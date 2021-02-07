import { Moment } from 'moment';
import { IIdentity } from 'app/shared/model/identity.model';
import { IPhone } from 'app/shared/model/phone.model';
import { IPastCities } from 'app/shared/model/past-cities.model';

export interface IPerson {
  id?: number;
  name?: string;
  surname?: string;
  birthday?: Moment;
  identity?: IIdentity;
  phones?: IPhone[];
  cities?: IPastCities[];
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public birthday?: Moment,
    public identity?: IIdentity,
    public phones?: IPhone[],
    public cities?: IPastCities[]
  ) {}
}
