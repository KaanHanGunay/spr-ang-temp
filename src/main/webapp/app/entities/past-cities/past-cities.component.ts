import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPastCities } from 'app/shared/model/past-cities.model';
import { PastCitiesService } from './past-cities.service';
import { PastCitiesDeleteDialogComponent } from './past-cities-delete-dialog.component';

@Component({
  selector: 'jhi-past-cities',
  templateUrl: './past-cities.component.html',
})
export class PastCitiesComponent implements OnInit, OnDestroy {
  pastCities?: IPastCities[];
  eventSubscriber?: Subscription;

  constructor(protected pastCitiesService: PastCitiesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pastCitiesService.query().subscribe((res: HttpResponse<IPastCities[]>) => (this.pastCities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPastCities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPastCities): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPastCities(): void {
    this.eventSubscriber = this.eventManager.subscribe('pastCitiesListModification', () => this.loadAll());
  }

  delete(pastCities: IPastCities): void {
    const modalRef = this.modalService.open(PastCitiesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pastCities = pastCities;
  }
}
