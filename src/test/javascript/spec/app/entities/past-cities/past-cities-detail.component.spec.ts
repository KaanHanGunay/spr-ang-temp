import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModernProjectTestModule } from '../../../test.module';
import { PastCitiesDetailComponent } from 'app/entities/past-cities/past-cities-detail.component';
import { PastCities } from 'app/shared/model/past-cities.model';

describe('Component Tests', () => {
  describe('PastCities Management Detail Component', () => {
    let comp: PastCitiesDetailComponent;
    let fixture: ComponentFixture<PastCitiesDetailComponent>;
    const route = ({ data: of({ pastCities: new PastCities(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ModernProjectTestModule],
        declarations: [PastCitiesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PastCitiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PastCitiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pastCities on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pastCities).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
