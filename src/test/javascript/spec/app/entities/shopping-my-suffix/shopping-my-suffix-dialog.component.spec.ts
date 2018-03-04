/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WearplusTestModule } from '../../../test.module';
import { ShoppingMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix-dialog.component';
import { ShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.service';
import { ShoppingMySuffix } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.model';

describe('Component Tests', () => {

    describe('ShoppingMySuffix Management Dialog Component', () => {
        let comp: ShoppingMySuffixDialogComponent;
        let fixture: ComponentFixture<ShoppingMySuffixDialogComponent>;
        let service: ShoppingMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ShoppingMySuffixDialogComponent],
                providers: [
                    ShoppingMySuffixService
                ]
            })
            .overrideTemplate(ShoppingMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShoppingMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shopping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shoppingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShoppingMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shopping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shoppingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
