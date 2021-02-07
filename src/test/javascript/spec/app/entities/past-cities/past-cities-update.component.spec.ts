import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ModernProjectTestModule } from '../../../test.module';
import { PastCitiesUpdateComponent } from 'app/entities/past-cities/past-cities-update.component';
import { PastCitiesService } from 'app/entities/past-cities/past-cities.service';
import { PastCities } from 'app/shared/model/past-cities.model';

describe('Component Tests', () => {
  describe('PastCities Management Update Component', () => {
    let comp: PastCitiesUpdateComponent;
    let fixture: ComponentFixture<PastCitiesUpdateComponent>;
    let service: PastCitiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ModernProjectTestModule],
        declarations: [PastCitiesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PastCitiesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PastCitiesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PastCitiesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PastCities(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PastCities();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
