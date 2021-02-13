import { Route } from '@angular/router';

import { MainStatsComponent } from 'app/components/statistics/main-stats/main-stats.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: MainStatsComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};
