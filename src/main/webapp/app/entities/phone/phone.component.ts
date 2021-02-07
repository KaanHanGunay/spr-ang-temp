import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhone } from 'app/shared/model/phone.model';
import { PhoneService } from './phone.service';
import { PhoneDeleteDialogComponent } from './phone-delete-dialog.component';

@Component({
  selector: 'jhi-phone',
  templateUrl: './phone.component.html',
})
export class PhoneComponent implements OnInit, OnDestroy {
  phones?: IPhone[];
  eventSubscriber?: Subscription;

  constructor(protected phoneService: PhoneService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.phoneService.query().subscribe((res: HttpResponse<IPhone[]>) => (this.phones = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhones();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhone): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPhones(): void {
    this.eventSubscriber = this.eventManager.subscribe('phoneListModification', () => this.loadAll());
  }

  delete(phone: IPhone): void {
    const modalRef = this.modalService.open(PhoneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phone = phone;
  }
}
