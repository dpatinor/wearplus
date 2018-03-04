/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WearplusTestModule } from '../../../test.module';
import { ClothesMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix-dialog.component';
import { ClothesMySuffixService } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.service';
import { ClothesMySuffix } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.model';
import { TypeClothesMySuffixService } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix';

describe('Component Tests', () => {

    describe('ClothesMySuffix Management Dialog Component', () => {
        let comp: ClothesMySuffixDialogComponent;
        let fixture: ComponentFixture<ClothesMySuffixDialogComponent>;
        let service: ClothesMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ClothesMySuffixDialogComponent],
                providers: [
                    TypeClothesMySuffixService,
                    ClothesMySuffixService
                ]
            })
            .overrideTemplate(ClothesMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClothesMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClothesMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClothesMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.clothes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clothesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClothesMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.clothes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clothesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
