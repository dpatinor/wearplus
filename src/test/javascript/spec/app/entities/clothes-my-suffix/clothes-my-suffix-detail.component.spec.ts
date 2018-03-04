/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WearplusTestModule } from '../../../test.module';
import { ClothesMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix-detail.component';
import { ClothesMySuffixService } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.service';
import { ClothesMySuffix } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.model';

describe('Component Tests', () => {

    describe('ClothesMySuffix Management Detail Component', () => {
        let comp: ClothesMySuffixDetailComponent;
        let fixture: ComponentFixture<ClothesMySuffixDetailComponent>;
        let service: ClothesMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ClothesMySuffixDetailComponent],
                providers: [
                    ClothesMySuffixService
                ]
            })
            .overrideTemplate(ClothesMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClothesMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClothesMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ClothesMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clothes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
