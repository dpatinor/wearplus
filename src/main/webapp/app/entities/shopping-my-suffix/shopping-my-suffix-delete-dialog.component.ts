import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { ShoppingMySuffixPopupService } from './shopping-my-suffix-popup.service';
import { ShoppingMySuffixService } from './shopping-my-suffix.service';

@Component({
    selector: 'jhi-shopping-my-suffix-delete-dialog',
    templateUrl: './shopping-my-suffix-delete-dialog.component.html'
})
export class ShoppingMySuffixDeleteDialogComponent {

    shopping: ShoppingMySuffix;

    constructor(
        private shoppingService: ShoppingMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shoppingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shoppingListModification',
                content: 'Deleted an shopping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shopping-my-suffix-delete-popup',
    template: ''
})
export class ShoppingMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingPopupService: ShoppingMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shoppingPopupService
                .open(ShoppingMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
