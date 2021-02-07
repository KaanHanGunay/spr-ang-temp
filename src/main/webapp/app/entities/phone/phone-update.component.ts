import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhone, Phone } from 'app/shared/model/phone.model';
import { PhoneService } from './phone.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-phone-update',
  templateUrl: './phone-update.component.html',
})
export class PhoneUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    startYear: [],
    endYear: [],
    person: [],
  });

  constructor(
    protected phoneService: PhoneService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phone }) => {
      this.updateForm(phone);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(phone: IPhone): void {
    this.editForm.patchValue({
      id: phone.id,
      phoneNumber: phone.phoneNumber,
      startYear: phone.startYear,
      endYear: phone.endYear,
      person: phone.person,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phone = this.createFromForm();
    if (phone.id !== undefined) {
      this.subscribeToSaveResponse(this.phoneService.update(phone));
    } else {
      this.subscribeToSaveResponse(this.phoneService.create(phone));
    }
  }

  private createFromForm(): IPhone {
    return {
      ...new Phone(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      startYear: this.editForm.get(['startYear'])!.value,
      endYear: this.editForm.get(['endYear'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhone>>): void {
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

  trackById(index: number, item: IPerson): any {
    return item.id;
  }
}
