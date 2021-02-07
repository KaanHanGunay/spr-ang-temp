import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdentity, Identity } from 'app/shared/model/identity.model';
import { IdentityService } from './identity.service';

@Component({
  selector: 'jhi-identity-update',
  templateUrl: './identity-update.component.html',
})
export class IdentityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identityNumber: [],
    motherName: [],
    fatherName: [],
  });

  constructor(protected identityService: IdentityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identity }) => {
      this.updateForm(identity);
    });
  }

  updateForm(identity: IIdentity): void {
    this.editForm.patchValue({
      id: identity.id,
      identityNumber: identity.identityNumber,
      motherName: identity.motherName,
      fatherName: identity.fatherName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const identity = this.createFromForm();
    if (identity.id !== undefined) {
      this.subscribeToSaveResponse(this.identityService.update(identity));
    } else {
      this.subscribeToSaveResponse(this.identityService.create(identity));
    }
  }

  private createFromForm(): IIdentity {
    return {
      ...new Identity(),
      id: this.editForm.get(['id'])!.value,
      identityNumber: this.editForm.get(['identityNumber'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      fatherName: this.editForm.get(['fatherName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentity>>): void {
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
}
