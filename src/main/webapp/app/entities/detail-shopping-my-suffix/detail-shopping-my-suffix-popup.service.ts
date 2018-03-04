import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DetailShoppingMySuffix } from './detail-shopping-my-suffix.model';
import { DetailShoppingMySuffixService } from './detail-shopping-my-suffix.service';

@Injectable()
export class DetailShoppingMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detailShoppingService: DetailShoppingMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.detailShoppingService.find(id)
                    .subscribe((detailShoppingResponse: HttpResponse<DetailShoppingMySuffix>) => {
                        const detailShopping: DetailShoppingMySuffix = detailShoppingResponse.body;
                        this.ngbModalRef = this.detailShoppingModalRef(component, detailShopping);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.detailShoppingModalRef(component, new DetailShoppingMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    detailShoppingModalRef(component: Component, detailShopping: DetailShoppingMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detailShopping = detailShopping;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
