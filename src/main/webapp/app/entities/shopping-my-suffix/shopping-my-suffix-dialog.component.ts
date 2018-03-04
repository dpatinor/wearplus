import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { ShoppingMySuffixPopupService } from './shopping-my-suffix-popup.service';
import { ShoppingMySuffixService } from './shopping-my-suffix.service';

@Component({
    selector: 'jhi-shopping-my-suffix-dialog',
    templateUrl: './shopping-my-suffix-dialog.component.html'
})
export class ShoppingMySuffixDialogComponent implements OnInit {

    shopping: ShoppingMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private shoppingService: ShoppingMySuffixService,
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
