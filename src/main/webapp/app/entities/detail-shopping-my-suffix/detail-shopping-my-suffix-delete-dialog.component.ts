import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetailShoppingMySuffix } from './detail-shopping-my-suffix.model';
import { DetailShoppingMySuffixPopupService } from './detail-shopping-my-suffix-popup.service';
import { DetailShoppingMySuffixService } from './detail-shopping-my-suffix.service';

@Component({
    selector: 'jhi-detail-shopping-my-suffix-delete-dialog',
    templateUrl: './detail-shopping-my-suffix-delete-dialog.component.html'
})
export class DetailShoppingMySuffixDeleteDialogComponent {

    detailShopping: DetailShoppingMySuffix;

    constructor(
        private detailShoppingService: DetailShoppingMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailShoppingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detailShoppingListModification',
                content: 'Deleted an detailShopping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detail-shopping-my-suffix-delete-popup',
    template: ''
})
export class DetailShoppingMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailShoppingPopupService: DetailShoppingMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detailShoppingPopupService
                .open(DetailShoppingMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
