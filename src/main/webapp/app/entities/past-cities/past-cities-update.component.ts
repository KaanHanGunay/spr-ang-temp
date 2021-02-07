import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPastCities, PastCities } from 'app/shared/model/past-cities.model';
import { PastCitiesService } from './past-cities.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-past-cities-update',
  templateUrl: './past-cities-update.component.html',
})
export class PastCitiesUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    city: [],
    startYear: [],
    endYear: [],
    person: [],
  });

  constructor(
    protected pastCitiesService: PastCitiesService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pastCities }) => {
      this.updateForm(pastCities);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(pastCities: IPastCities): void {
    this.editForm.patchValue({
      id: pastCities.id,
      city: pastCities.city,
      startYear: pastCities.startYear,
      endYear: pastCities.endYear,
      person: pastCities.person,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pastCities = this.createFromForm();
    if (pastCities.id !== undefined) {
      this.subscribeToSaveResponse(this.pastCitiesService.update(pastCities));
    } else {
      this.subscribeToSaveResponse(this.pastCitiesService.create(pastCities));
    }
  }

  private createFromForm(): IPastCities {
    return {
      ...new PastCities(),
      id: this.editForm.get(['id'])!.value,
      city: this.editForm.get(['city'])!.value,
      startYear: this.editForm.get(['startYear'])!.value,
      endYear: this.editForm.get(['endYear'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPastCities>>): void {
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
