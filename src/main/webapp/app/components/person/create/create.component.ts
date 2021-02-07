import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { IPerson, Person } from 'app/shared/model/person.model';
import { IPhone, Phone } from 'app/shared/model/phone.model';
import { IPastCities, PastCities } from 'app/shared/model/past-cities.model';
import { PersonService } from 'app/entities/person/person.service';
import { Identity } from 'app/shared/model/identity.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'jhi-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss'],
})
export class CreateComponent implements OnInit {
  form?: FormGroup;
  newPerson?: IPerson;
  phones: IPhone[] = [new Phone()];
  pastCities: IPastCities[] = [new PastCities()];

  constructor(public personService: PersonService, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl(null),
      surname: new FormControl(null),
      phone1: new FormControl(null),
      phoneStart1: new FormControl(null),
      phoneEnd1: new FormControl(null),
      phoneButton1: new FormControl(null),
      city1: new FormControl(null),
      cityStart1: new FormControl(null),
      cityEnd1: new FormControl(null),
    });
  }

  createNew(): void {
    this.newPerson = new Person();
    this.newPerson.name = this.form?.get('name')?.value;
    this.newPerson.surname = this.form?.get('surname')?.value;

    const identity = new Identity();
    identity.fatherName = '';
    identity.motherName = '';
    this.newPerson.identity = identity;

    const phones: IPhone[] = [];
    this.phones.forEach(ph => {
      if (ph.phoneNumber) {
        phones.push(ph);
      }
    });
    this.newPerson.phones = phones;

    const cities: IPastCities[] = [];
    this.pastCities.forEach(cty => {
      if (cty.city) {
        cities.push(cty);
      }
    });
    this.newPerson.cities = cities;

    this.personService.create(this.newPerson).subscribe(_ => {
      this._snackBar.open('Yeni Şahıs Kaydı Eklendi!', 'Kapat', { duration: 2000 });
    });
  }

  addNewPhone(): void {
    this.phones.push(new Phone());
    this.form?.addControl('phone' + this.phones.length, new FormControl(null));
    this.form?.addControl('phoneStart' + this.phones.length, new FormControl(null));
    this.form?.addControl('phoneEnd' + this.phones.length, new FormControl(null));
  }

  addNewCity(): void {
    this.pastCities.push(new PastCities());
    this.form?.addControl('city' + this.pastCities.length, new FormControl(null));
    this.form?.addControl('cityStart' + this.pastCities.length, new FormControl(null));
    this.form?.addControl('cityEnd' + this.pastCities.length, new FormControl(null));
  }
}
