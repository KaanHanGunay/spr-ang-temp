import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IIdentity } from 'app/shared/model/identity.model';
import { IdentityService } from 'app/entities/identity/identity.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  identities: IIdentity[] = [];
  birthdayDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    surname: [],
    birthday: [],
    identity: [],
  });

  constructor(
    protected personService: PersonService,
    protected identityService: IdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);

      this.identityService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<IIdentity[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IIdentity[]) => {
          if (!person.identity || !person.identity.id) {
            this.identities = resBody;
          } else {
            this.identityService
              .find(person.identity.id)
              .pipe(
                map((subRes: HttpResponse<IIdentity>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IIdentity[]) => (this.identities = concatRes));
          }
        });
    });
  }

  updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      name: person.name,
      surname: person.surname,
      birthday: person.birthday,
      identity: person.identity,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  private createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      birthday: this.editForm.get(['birthday'])!.value,
      identity: this.editForm.get(['identity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IIdentity): any {
    return item.id;
  }
}
