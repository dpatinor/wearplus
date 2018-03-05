import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { ShoppingMySuffixPopupService } from './shopping-my-suffix-popup.service';
import { ShoppingMySuffixService } from './shopping-my-suffix.service';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { ClothesMySuffix } from './clothes-my-suffix.model';

@Component({
    selector: 'jhi-shopping-my-suffix-dialog',
    templateUrl: './shopping-my-suffix-dialog.component.html'
})
export class ShoppingMySuffixDialogComponent implements OnInit {

    shopping: ShoppingMySuffix;
    isSaving: boolean;

    public types: TypeClothesMySuffix[];
    public type1: TypeClothesMySuffix = {};
    public type2: TypeClothesMySuffix = {};
    public type3: TypeClothesMySuffix = {};
    public type4: TypeClothesMySuffix = {};
    public type5: TypeClothesMySuffix = {};
    public clothes1: ClothesMySuffix[];
    public clothes2: ClothesMySuffix[];
    public clothes3: ClothesMySuffix[];
    public clothes4: ClothesMySuffix[];
    public clothes5: ClothesMySuffix[];


    constructor(
        public activeModal: NgbActiveModal,
        private shoppingService: ShoppingMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.shoppingService.queryTypeClothes()
            .subscribe(
                (res: HttpResponse<TypeClothesMySuffix[]>) => this.onSuccessTypes(res.body, res.headers),
        );
    }

    private onSuccessTypes(data, headers) {
        this.types = data;
    }

    type1Selected(event) {
        if(this.type1 && this.type1.id) {
            this.shoppingService.queryClothes(this.type1.id)
                .subscribe(
                    (res: HttpResponse<TypeClothesMySuffix[]>) => this.onSuccessClothes1(res.body, res.headers),
            );
        }
    }

    private onSuccessClothes1(data, headers) {
        this.clothes1 = data;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shopping.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shoppingService.update(this.shopping));
        } else {
            this.subscribeToSaveResponse(
                this.shoppingService.create(this.shopping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ShoppingMySuffix>>) {
        result.subscribe((res: HttpResponse<ShoppingMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ShoppingMySuffix) {
        this.eventManager.broadcast({ name: 'shoppingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-shopping-my-suffix-popup',
    template: ''
})
export class ShoppingMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingPopupService: ShoppingMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shoppingPopupService
                    .open(ShoppingMySuffixDialogComponent as Component, params['id']);
            } else {
                this.shoppingPopupService
                    .open(ShoppingMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
