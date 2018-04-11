import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Prestataire } from './prestataire.model';
import { PrestataireService } from './prestataire.service';

@Component({
    selector: 'jhi-prestataire-detail',
    templateUrl: './prestataire-detail.component.html'
})
export class PrestataireDetailComponent implements OnInit, OnDestroy {

    prestataire: Prestataire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private prestataireService: PrestataireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestataires();
    }

    load(id) {
        this.prestataireService.find(id)
            .subscribe((prestataireResponse: HttpResponse<Prestataire>) => {
                this.prestataire = prestataireResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestataires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestataireListModification',
            (response) => this.load(this.prestataire.id)
        );
    }
}
