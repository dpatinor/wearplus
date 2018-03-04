/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WearplusTestModule } from '../../../test.module';
import { DetailShoppingMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix-detail.component';
import { DetailShoppingMySuffixService } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.service';
import { DetailShoppingMySuffix } from '../../../../../../main/webapp/app/entities/detail-shopping-my-suffix/detail-shopping-my-suffix.model';

describe('Component Tests', () => {

    describe('DetailShoppingMySuffix Management Detail Component', () => {
        let comp: DetailShoppingMySuffixDetailComponent;
        let fixture: ComponentFixture<DetailShoppingMySuffixDetailComponent>;
        let service: DetailShoppingMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [DetailShoppingMySuffixDetailComponent],
                providers: [
                    DetailShoppingMySuffixService
                ]
            })
            .overrideTemplate(DetailShoppingMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailShoppingMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailShoppingMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DetailShoppingMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.detailShopping).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
