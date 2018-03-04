import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { TypeClothesMySuffixPopupService } from './type-clothes-my-suffix-popup.service';
import { TypeClothesMySuffixService } from './type-clothes-my-suffix.service';

@Component({
    selector: 'jhi-type-clothes-my-suffix-delete-dialog',
    templateUrl: './type-clothes-my-suffix-delete-dialog.component.html'
})
export class TypeClothesMySuffixDeleteDialogComponent {

    typeClothes: TypeClothesMySuffix;

    constructor(
        private typeClothesService: TypeClothesMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeClothesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeClothesListModification',
                content: 'Deleted an typeClothes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-clothes-my-suffix-delete-popup',
    template: ''
})
export class TypeClothesMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeClothesPopupService: TypeClothesMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeClothesPopupService
                .open(TypeClothesMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
