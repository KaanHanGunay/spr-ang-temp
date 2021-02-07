import { Route } from '@angular/router';

import { CreateComponent } from 'app/components/person/create/create.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: CreateComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};
