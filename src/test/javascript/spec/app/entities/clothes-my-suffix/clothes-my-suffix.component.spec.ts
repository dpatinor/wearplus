/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WearplusTestModule } from '../../../test.module';
import { ClothesMySuffixComponent } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.component';
import { ClothesMySuffixService } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.service';
import { ClothesMySuffix } from '../../../../../../main/webapp/app/entities/clothes-my-suffix/clothes-my-suffix.model';

describe('Component Tests', () => {

    describe('ClothesMySuffix Management Component', () => {
        let comp: ClothesMySuffixComponent;
        let fixture: ComponentFixture<ClothesMySuffixComponent>;
        let service: ClothesMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WearplusTestModule],
                declarations: [ClothesMySuffixComponent],
                providers: [
                    ClothesMySuffixService
                ]
            })
            .overrideTemplate(ClothesMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClothesMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClothesMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ClothesMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clothes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
