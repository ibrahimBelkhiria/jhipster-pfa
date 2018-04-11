/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PfaprojectTestModule } from '../../../test.module';
import { PrestataireDetailComponent } from '../../../../../../main/webapp/app/entities/prestataire/prestataire-detail.component';
import { PrestataireService } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.service';
import { Prestataire } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.model';

describe('Component Tests', () => {

    describe('Prestataire Management Detail Component', () => {
        let comp: PrestataireDetailComponent;
        let fixture: ComponentFixture<PrestataireDetailComponent>;
        let service: PrestataireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfaprojectTestModule],
                declarations: [PrestataireDetailComponent],
                providers: [
                    PrestataireService
                ]
            })
            .overrideTemplate(PrestataireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestataireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestataireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Prestataire(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestataire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
