import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Prestataire } from './prestataire.model';
import { PrestatairePopupService } from './prestataire-popup.service';
import { PrestataireService } from './prestataire.service';

@Component({
    selector: 'jhi-prestataire-delete-dialog',
    templateUrl: './prestataire-delete-dialog.component.html'
})
export class PrestataireDeleteDialogComponent {

    prestataire: Prestataire;

    constructor(
        private prestataireService: PrestataireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestataireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestataireListModification',
                content: 'Deleted an prestataire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prestataire-delete-popup',
    template: ''
})
export class PrestataireDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestatairePopupService: PrestatairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prestatairePopupService
                .open(PrestataireDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
