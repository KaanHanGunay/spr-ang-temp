import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModernProjectTestModule } from '../../../test.module';
import { PhoneDetailComponent } from 'app/entities/phone/phone-detail.component';
import { Phone } from 'app/shared/model/phone.model';

describe('Component Tests', () => {
  describe('Phone Management Detail Component', () => {
    let comp: PhoneDetailComponent;
    let fixture: ComponentFixture<PhoneDetailComponent>;
    const route = ({ data: of({ phone: new Phone(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ModernProjectTestModule],
        declarations: [PhoneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhoneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhoneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phone on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phone).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
