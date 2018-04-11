/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PfaprojectTestModule } from '../../../test.module';
import { PrestataireComponent } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.component';
import { PrestataireService } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.service';
import { Prestataire } from '../../../../../../main/webapp/app/entities/prestataire/prestataire.model';

describe('Component Tests', () => {

    describe('Prestataire Management Component', () => {
        let comp: PrestataireComponent;
        let fixture: ComponentFixture<PrestataireComponent>;
        let service: PrestataireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfaprojectTestModule],
                declarations: [PrestataireComponent],
                providers: [
                    PrestataireService
                ]
            })
            .overrideTemplate(PrestataireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestataireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestataireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Prestataire(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestataires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
