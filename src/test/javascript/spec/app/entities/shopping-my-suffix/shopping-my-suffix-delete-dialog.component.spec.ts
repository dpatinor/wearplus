/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WearplusTestModule } from '../../../test.module';
import { ShoppingMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix-delete-dialog.component';
import { ShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.service';

describe('Component Tests', () => {

    describe('ShoppingMySuffix Management Delete Component', () => {
        let comp: ShoppingMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<ShoppingMySuffixDeleteDialogComponent>;
        let service: ShoppingMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ShoppingMySuffixDeleteDialogComponent],
                providers: [
                    ShoppingMySuffixService
                ]
            })
            .overrideTemplate(ShoppingMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingMySuffixService);
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
