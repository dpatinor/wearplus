import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClothesMySuffix } from './clothes-my-suffix.model';
import { ClothesMySuffixPopupService } from './clothes-my-suffix-popup.service';
import { ClothesMySuffixService } from './clothes-my-suffix.service';

@Component({
    selector: 'jhi-clothes-my-suffix-delete-dialog',
    templateUrl: './clothes-my-suffix-delete-dialog.component.html'
})
export class ClothesMySuffixDeleteDialogComponent {

    clothes: ClothesMySuffix;

    constructor(
        private clothesService: ClothesMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clothesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clothesListModification',
                content: 'Deleted an clothes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clothes-my-suffix-delete-popup',
    template: ''
})
export class ClothesMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clothesPopupService: ClothesMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clothesPopupService
                .open(ClothesMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
