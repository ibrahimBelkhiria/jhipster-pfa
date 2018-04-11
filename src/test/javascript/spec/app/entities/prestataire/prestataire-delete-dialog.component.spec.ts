/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PfaprojectTestModule } from '../../../test.module';
import { PrestataireDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/prestataire/prestataire-delete-dialog.component';
import { PrestataireService } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.service';

describe('Component Tests', () => {

    describe('Prestataire Management Delete Component', () => {
        let comp: PrestataireDeleteDialogComponent;
        let fixture: ComponentFixture<PrestataireDeleteDialogComponent>;
        let service: PrestataireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfaprojectTestModule],
                declarations: [PrestataireDeleteDialogComponent],
                providers: [
                    PrestataireService
                ]
            })
            .overrideTemplate(PrestataireDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestataireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestataireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
