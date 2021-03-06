import { NgModule } from '@angular/core';
import { ModernProjectSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { JhMaterialModule } from 'app/shared/jh-material.module';
import { ChartsModule } from 'ng2-charts';

@NgModule({
  imports: [JhMaterialModule, ModernProjectSharedLibsModule, ChartsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent],
  exports: [
    JhMaterialModule,
    ModernProjectSharedLibsModule,
    ChartsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
})
export class ModernProjectSharedModule {}
