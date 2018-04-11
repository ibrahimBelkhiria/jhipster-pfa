import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PfaprojectProjectModule } from './project/project.module';
import { PfaprojectPrestataireModule } from './prestataire/prestataire.module';
import { PfaprojectCategoryModule } from './category/category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PfaprojectProjectModule,
        PfaprojectPrestataireModule,
        PfaprojectCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfaprojectEntityModule {}
