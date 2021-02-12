import { Route } from '@angular/router';

import { PieChartComponent } from 'app/components/statistics/pie-chart/pie-chart.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: PieChartComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};
