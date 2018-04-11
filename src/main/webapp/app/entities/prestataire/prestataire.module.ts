import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfaprojectSharedModule } from '../../shared';
import {
    PrestataireService,
    PrestatairePopupService,
    PrestataireComponent,
    PrestataireDetailComponent,
    PrestataireDialogComponent,
    PrestatairePopupComponent,
    PrestataireDeletePopupComponent,
    PrestataireDeleteDialogComponent,
    prestataireRoute,
    prestatairePopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestataireRoute,
    ...prestatairePopupRoute,
];

@NgModule({
    imports: [
        PfaprojectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrestataireComponent,
        PrestataireDetailComponent,
        PrestataireDialogComponent,
        PrestataireDeleteDialogComponent,
        PrestatairePopupComponent,
        PrestataireDeletePopupComponent,
    ],
    entryComponents: [
        PrestataireComponent,
        PrestataireDialogComponent,
        PrestatairePopupComponent,
        PrestataireDeleteDialogComponent,
        PrestataireDeletePopupComponent,
    ],
    providers: [
        PrestataireService,
        PrestatairePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfaprojectPrestataireModule {}
