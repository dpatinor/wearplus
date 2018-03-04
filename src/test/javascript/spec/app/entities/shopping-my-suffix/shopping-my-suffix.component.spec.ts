/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WearplusTestModule } from '../../../test.module';
import { ShoppingMySuffixComponent } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.component';
import { ShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.service';
import { ShoppingMySuffix } from '../../../../../../main/webapp/app/entities/shopping-my-suffix/shopping-my-suffix.model';

describe('Component Tests', () => {

    describe('ShoppingMySuffix Management Component', () => {
        let comp: ShoppingMySuffixComponent;
        let fixture: ComponentFixture<ShoppingMySuffixComponent>;
        let service: ShoppingMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ShoppingMySuffixComponent],
                providers: [
                    ShoppingMySuffixService
                ]
            })
            .overrideTemplate(ShoppingMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ShoppingMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shoppings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
