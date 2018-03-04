/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WearplusTestModule } from '../../../test.module';
import { TypeClothesMySuffixComponent } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix.component';
import { TypeClothesMySuffixService } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix.service';
import { TypeClothesMySuffix } from '../../../../../../main/webapp/app/entities/type-clothes-my-suffix/type-clothes-my-suffix.model';

describe('Component Tests', () => {

    describe('TypeClothesMySuffix Management Component', () => {
        let comp: TypeClothesMySuffixComponent;
        let fixture: ComponentFixture<TypeClothesMySuffixComponent>;
        let service: TypeClothesMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [TypeClothesMySuffixComponent],
                providers: [
                    TypeClothesMySuffixService
                ]
            })
            .overrideTemplate(TypeClothesMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeClothesMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeClothesMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeClothesMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeClothes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
