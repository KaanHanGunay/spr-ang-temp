export interface IIdentity {
  id?: number;
  identityNumber?: string;
  motherName?: string;
  fatherName?: string;
}

export class Identity implements IIdentity {
  constructor(public id?: number, public identityNumber?: string, public motherName?: string, public fatherName?: string) {}
}
