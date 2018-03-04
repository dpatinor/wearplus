import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClothesMySuffix } from './clothes-my-suffix.model';
import { ClothesMySuffixPopupService } from './clothes-my-suffix-popup.service';
import { ClothesMySuffixService } from './clothes-my-suffix.service';
import { TypeClothesMySuffix, TypeClothesMySuffixService } from '../type-clothes-my-suffix';

@Component({
    selector: 'jhi-clothes-my-suffix-dialog',
    templateUrl: './clothes-my-suffix-dialog.component.html'
})
export class ClothesMySuffixDialogComponent implements OnInit {

    clothes: ClothesMySuffix;
    isSaving: boolean;

    typeclothes: TypeClothesMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private clothesService: ClothesMySuffixService,
        private typeClothesService: TypeClothesMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.typeClothesService
            .query({filter: 'clothes-is-null'})
            .subscribe((res: HttpResponse<TypeClothesMySuffix[]>) => {
                if (!this.clothes.typeClothesId) {
                    this.typeclothes = res.body;
                } else {
                    this.typeClothesService
                        .find(this.clothes.typeClothesId)
                        .subscribe((subRes: HttpResponse<TypeClothesMySuffix>) => {
                            this.typeclothes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clothes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clothesService.update(this.clothes));
        } else {
            this.subscribeToSaveResponse(
                this.clothesService.create(this.clothes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ClothesMySuffix>>) {
        result.subscribe((res: HttpResponse<ClothesMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ClothesMySuffix) {
        this.eventManager.broadcast({ name: 'clothesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTypeClothesById(index: number, item: TypeClothesMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-clothes-my-suffix-popup',
    template: ''
})
export class ClothesMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clothesPopupService: ClothesMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clothesPopupService
                    .open(ClothesMySuffixDialogComponent as Component, params['id']);
            } else {
                this.clothesPopupService
                    .open(ClothesMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
