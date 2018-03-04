import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { TypeClothesMySuffixService } from './type-clothes-my-suffix.service';

@Injectable()
export class TypeClothesMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeClothesService: TypeClothesMySuffixService

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
                this.typeClothesService.find(id)
                    .subscribe((typeClothesResponse: HttpResponse<TypeClothesMySuffix>) => {
                        const typeClothes: TypeClothesMySuffix = typeClothesResponse.body;
                        this.ngbModalRef = this.typeClothesModalRef(component, typeClothes);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeClothesModalRef(component, new TypeClothesMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeClothesModalRef(component: Component, typeClothes: TypeClothesMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeClothes = typeClothes;
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
