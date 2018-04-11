import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrestataireComponent } from './prestataire.component';
import { PrestataireDetailComponent } from './prestataire-detail.component';
import { PrestatairePopupComponent } from './prestataire-dialog.component';
import { PrestataireDeletePopupComponent } from './prestataire-delete-dialog.component';

export const prestataireRoute: Routes = [
    {
        path: 'prestataire',
        component: PrestataireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfaprojectApp.prestataire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestataire/:id',
        component: PrestataireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfaprojectApp.prestataire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestatairePopupRoute: Routes = [
    {
        path: 'prestataire-new',
        component: PrestatairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfaprojectApp.prestataire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestataire/:id/edit',
        component: PrestatairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfaprojectApp.prestataire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestataire/:id/delete',
        component: PrestataireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfaprojectApp.prestataire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
