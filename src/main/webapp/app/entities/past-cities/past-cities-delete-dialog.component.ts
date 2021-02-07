import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPastCities } from 'app/shared/model/past-cities.model';
import { PastCitiesService } from './past-cities.service';

@Component({
  templateUrl: './past-cities-delete-dialog.component.html',
})
export class PastCitiesDeleteDialogComponent {
  pastCities?: IPastCities;

  constructor(
    protected pastCitiesService: PastCitiesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pastCitiesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pastCitiesListModification');
      this.activeModal.close();
    });
  }
}
