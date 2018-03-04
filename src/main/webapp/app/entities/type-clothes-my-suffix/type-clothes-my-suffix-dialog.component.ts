import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { TypeClothesMySuffixPopupService } from './type-clothes-my-suffix-popup.service';
import { TypeClothesMySuffixService } from './type-clothes-my-suffix.service';

@Component({
    selector: 'jhi-type-clothes-my-suffix-dialog',
    templateUrl: './type-clothes-my-suffix-dialog.component.html'
})
export class TypeClothesMySuffixDialogComponent implements OnInit {

    typeClothes: TypeClothesMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeClothesService: TypeClothesMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.typeClothes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeClothesService.update(this.typeClothes));
        } else {
            this.subscribeToSaveResponse(
                this.typeClothesService.create(this.typeClothes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeClothesMySuffix>>) {
        result.subscribe((res: HttpResponse<TypeClothesMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeClothesMySuffix) {
        this.eventManager.broadcast({ name: 'typeClothesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-clothes-my-suffix-popup',
    template: ''
})
export class TypeClothesMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeClothesPopupService: TypeClothesMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeClothesPopupService
                    .open(TypeClothesMySuffixDialogComponent as Component, params['id']);
            } else {
                this.typeClothesPopupService
                    .open(TypeClothesMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
