import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Prestataire } from './prestataire.model';
import { PrestatairePopupService } from './prestataire-popup.service';
import { PrestataireService } from './prestataire.service';
import { Category, CategoryService } from '../category';

@Component({
    selector: 'jhi-prestataire-dialog',
    templateUrl: './prestataire-dialog.component.html'
})
export class PrestataireDialogComponent implements OnInit {

    prestataire: Prestataire;
    isSaving: boolean;

    categories: Category[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private prestataireService: PrestataireService,
        private categoryService: CategoryService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.categoryService.query()
            .subscribe((res: HttpResponse<Category[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.prestataire, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestataire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestataireService.update(this.prestataire));
        } else {
            this.subscribeToSaveResponse(
                this.prestataireService.create(this.prestataire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Prestataire>>) {
        result.subscribe((res: HttpResponse<Prestataire>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Prestataire) {
        this.eventManager.broadcast({ name: 'prestataireListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestataire-popup',
    template: ''
})
export class PrestatairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestatairePopupService: PrestatairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prestatairePopupService
                    .open(PrestataireDialogComponent as Component, params['id']);
            } else {
                this.prestatairePopupService
                    .open(PrestataireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
