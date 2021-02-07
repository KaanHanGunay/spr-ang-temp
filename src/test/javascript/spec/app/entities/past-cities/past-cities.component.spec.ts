import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ModernProjectTestModule } from '../../../test.module';
import { PastCitiesComponent } from 'app/entities/past-cities/past-cities.component';
import { PastCitiesService } from 'app/entities/past-cities/past-cities.service';
import { PastCities } from 'app/shared/model/past-cities.model';

describe('Component Tests', () => {
  describe('PastCities Management Component', () => {
    let comp: PastCitiesComponent;
    let fixture: ComponentFixture<PastCitiesComponent>;
    let service: PastCitiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ModernProjectTestModule],
        declarations: [PastCitiesComponent],
      })
        .overrideTemplate(PastCitiesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PastCitiesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PastCitiesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PastCities(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pastCities && comp.pastCities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
