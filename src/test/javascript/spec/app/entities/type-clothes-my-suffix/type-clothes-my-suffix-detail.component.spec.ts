/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WearplusTestModule } from '../../../test.module';
import { TypeClothesMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix-detail.component';
import { TypeClothesMySuffixService } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix.service';
import { TypeClothesMySuffix } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix.model';

describe('Component Tests', () => {

    describe('TypeClothesMySuffix Management Detail Component', () => {
        let comp: TypeClothesMySuffixDetailComponent;
        let fixture: ComponentFixture<TypeClothesMySuffixDetailComponent>;
        let service: TypeClothesMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [TypeClothesMySuffixDetailComponent],
                providers: [
                    TypeClothesMySuffixService
                ]
            })
            .overrideTemplate(TypeClothesMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeClothesMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeClothesMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeClothesMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeClothes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
