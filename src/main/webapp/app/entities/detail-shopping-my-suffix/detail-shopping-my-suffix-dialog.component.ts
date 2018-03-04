import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetailShoppingMySuffix } from './detail-shopping-my-suffix.model';
import { DetailShoppingMySuffixPopupService } from './detail-shopping-my-suffix-popup.service';
import { DetailShoppingMySuffixService } from './detail-shopping-my-suffix.service';
import { ShoppingMySuffix, ShoppingMySuffixService } from '../shopping-my-suffix';
import { ClothesMySuffix, ClothesMySuffixService } from '../clothes-my-suffix';

@Component({
    selector: 'jhi-detail-shopping-my-suffix-dialog',
    templateUrl: './detail-shopping-my-suffix-dialog.component.html'
})
export class DetailShoppingMySuffixDialogComponent implements OnInit {

    detailShopping: DetailShoppingMySuffix;
    isSaving: boolean;

    shoppings: ShoppingMySuffix[];

    clothes: ClothesMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private detailShoppingService: DetailShoppingMySuffixService,
        private shoppingService: ShoppingMySuffixService,
        private clothesService: ClothesMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.shoppingService
            .query({filter: 'detailshopping-is-null'})
            .subscribe((res: HttpResponse<ShoppingMySuffix[]>) => {
                if (!this.detailShopping.shoppingId) {
                    this.shoppings = res.body;
                } else {
                    this.shoppingService
                        .find(this.detailShopping.shoppingId)
                        .subscribe((subRes: HttpResponse<ShoppingMySuffix>) => {
                            this.shoppings = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clothesService
            .query({filter: 'detailshopping-is-null'})
            .subscribe((res: HttpResponse<ClothesMySuffix[]>) => {
                if (!this.detailShopping.clothesId) {
                    this.clothes = res.body;
                } else {
                    this.clothesService
                        .find(this.detailShopping.clothesId)
                        .subscribe((subRes: HttpResponse<ClothesMySuffix>) => {
                            this.clothes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detailShopping.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detailShoppingService.update(this.detailShopping));
        } else {
            this.subscribeToSaveResponse(
                this.detailShoppingService.create(this.detailShopping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DetailShoppingMySuffix>>) {
        result.subscribe((res: HttpResponse<DetailShoppingMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DetailShoppingMySuffix) {
        this.eventManager.broadcast({ name: 'detailShoppingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackShoppingById(index: number, item: ShoppingMySuffix) {
        return item.id;
    }

    trackClothesById(index: number, item: ClothesMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-detail-shopping-my-suffix-popup',
    template: ''
})
export class DetailShoppingMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailShoppingPopupService: DetailShoppingMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detailShoppingPopupService
                    .open(DetailShoppingMySuffixDialogComponent as Component, params['id']);
            } else {
                this.detailShoppingPopupService
                    .open(DetailShoppingMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
