import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ModernProjectTestModule } from '../../../test.module';
import { PhoneComponent } from 'app/entities/phone/phone.component';
import { PhoneService } from 'app/entities/phone/phone.service';
import { Phone } from 'app/shared/model/phone.model';

describe('Component Tests', () => {
  describe('Phone Management Component', () => {
    let comp: PhoneComponent;
    let fixture: ComponentFixture<PhoneComponent>;
    let service: PhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ModernProjectTestModule],
        declarations: [PhoneComponent],
      })
        .overrideTemplate(PhoneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhoneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhoneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Phone(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.phones && comp.phones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
