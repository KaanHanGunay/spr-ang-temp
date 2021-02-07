import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModernProjectSharedModule } from 'app/shared/shared.module';
import { PastCitiesComponent } from './past-cities.component';
import { PastCitiesDetailComponent } from './past-cities-detail.component';
import { PastCitiesUpdateComponent } from './past-cities-update.component';
import { PastCitiesDeleteDialogComponent } from './past-cities-delete-dialog.component';
import { pastCitiesRoute } from './past-cities.route';

@NgModule({
  imports: [ModernProjectSharedModule, RouterModule.forChild(pastCitiesRoute)],
  declarations: [PastCitiesComponent, PastCitiesDetailComponent, PastCitiesUpdateComponent, PastCitiesDeleteDialogComponent],
  entryComponents: [PastCitiesDeleteDialogComponent],
})
export class ModernProjectPastCitiesModule {}
