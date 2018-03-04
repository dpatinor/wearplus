/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WearplusTestModule } from '../../../test.module';
import { ShoppingMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix-detail.component';
import { ShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.service';
import { ShoppingMySuffix } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.model';

describe('Component Tests', () => {

    describe('ShoppingMySuffix Management Detail Component', () => {
        let comp: ShoppingMySuffixDetailComponent;
        let fixture: ComponentFixture<ShoppingMySuffixDetailComponent>;
        let service: ShoppingMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ShoppingMySuffixDetailComponent],
                providers: [
                    ShoppingMySuffixService
                ]
            })
            .overrideTemplate(ShoppingMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ShoppingMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shopping).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
