import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPastCities, PastCities } from 'app/shared/model/past-cities.model';
import { PastCitiesService } from './past-cities.service';
import { PastCitiesComponent } from './past-cities.component';
import { PastCitiesDetailComponent } from './past-cities-detail.component';
import { PastCitiesUpdateComponent } from './past-cities-update.component';

@Injectable({ providedIn: 'root' })
export class PastCitiesResolve implements Resolve<IPastCities> {
  constructor(private service: PastCitiesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPastCities> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pastCities: HttpResponse<PastCities>) => {
          if (pastCities.body) {
            return of(pastCities.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PastCities());
  }
}

export const pastCitiesRoute: Routes = [
  {
    path: '',
    component: PastCitiesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'modernProjectApp.pastCities.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PastCitiesDetailComponent,
    resolve: {
      pastCities: PastCitiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'modernProjectApp.pastCities.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PastCitiesUpdateComponent,
    resolve: {
      pastCities: PastCitiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'modernProjectApp.pastCities.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PastCitiesUpdateComponent,
    resolve: {
      pastCities: PastCitiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'modernProjectApp.pastCities.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
