/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WearplusTestModule } from '../../../test.module';
import { DetailShoppingMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix-dialog.component';
import { DetailShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.service';
import { DetailShoppingMySuffix } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.model';
import { ShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-my-suffix';
import { ClothesMySuffixService } from '../../../../../../main/webapp/app/entities/clothes-my-suffix';

describe('Component Tests', () => {

    describe('DetailShoppingMySuffix Management Dialog Component', () => {
        let comp: DetailShoppingMySuffixDialogComponent;
        let fixture: ComponentFixture<DetailShoppingMySuffixDialogComponent>;
        let service: DetailShoppingMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [DetailShoppingMySuffixDialogComponent],
                providers: [
                    ShoppingMySuffixService,
                    ClothesMySuffixService,
                    DetailShoppingMySuffixService
                ]
            })
            .overrideTemplate(DetailShoppingMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailShoppingMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailShoppingMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetailShoppingMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.detailShopping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detailShoppingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetailShoppingMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.detailShopping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detailShoppingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
