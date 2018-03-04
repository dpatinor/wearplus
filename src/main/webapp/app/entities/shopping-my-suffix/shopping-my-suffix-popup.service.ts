import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { ShoppingMySuffixService } from './shopping-my-suffix.service';

@Injectable()
export class ShoppingMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private shoppingService: ShoppingMySuffixService

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
                this.shoppingService.find(id)
                    .subscribe((shoppingResponse: HttpResponse<ShoppingMySuffix>) => {
                        const shopping: ShoppingMySuffix = shoppingResponse.body;
                        shopping.dateOrder = this.datePipe
                            .transform(shopping.dateOrder, 'yyyy-MM-ddTHH:mm:ss');
                        shopping.dateDelivery = this.datePipe
                            .transform(shopping.dateDelivery, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.shoppingModalRef(component, shopping);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.shoppingModalRef(component, new ShoppingMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    shoppingModalRef(component: Component, shopping: ShoppingMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shopping = shopping;
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
