/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WearplusTestModule } from '../../../test.module';
import { DetailShoppingMySuffixComponent } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.component';
import { DetailShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.service';
import { DetailShoppingMySuffix } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.model';

describe('Component Tests', () => {

    describe('DetailShoppingMySuffix Management Component', () => {
        let comp: DetailShoppingMySuffixComponent;
        let fixture: ComponentFixture<DetailShoppingMySuffixComponent>;
        let service: DetailShoppingMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [DetailShoppingMySuffixComponent],
                providers: [
                    DetailShoppingMySuffixService
                ]
            })
            .overrideTemplate(DetailShoppingMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailShoppingMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailShoppingMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DetailShoppingMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.detailShoppings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
