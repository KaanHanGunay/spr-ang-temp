import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ModernProjectSharedModule } from 'app/shared/shared.module';
import { ModernProjectCoreModule } from 'app/core/core.module';
import { ModernProjectAppRoutingModule } from './app-routing.module';
import { ModernProjectHomeModule } from './home/home.module';
import { ModernProjectEntityModule } from './entities/entity.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { CreateComponent } from './components/person/create/create.component';
import { PieChartComponent } from './components/statistics/pie-chart/pie-chart.component';
import { MainStatsComponent } from './components/statistics/main-stats/main-stats.component';
import { DoughnutChartComponent } from './components/statistics/doughnut-chart/doughnut-chart.component';
import { BarChartComponent } from './components/statistics/bar-chart/bar-chart.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    ModernProjectSharedModule,
    ModernProjectCoreModule,
    ModernProjectHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ModernProjectEntityModule,
    ModernProjectAppRoutingModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    CreateComponent,
    PieChartComponent,
    MainStatsComponent,
    DoughnutChartComponent,
    BarChartComponent,
  ],
  bootstrap: [MainComponent],
})
export class ModernProjectAppModule {}
