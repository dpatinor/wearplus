import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeClothesMySuffixComponent } from './type-clothes-my-suffix.component';
import { TypeClothesMySuffixDetailComponent } from './type-clothes-my-suffix-detail.component';
import { TypeClothesMySuffixPopupComponent } from './type-clothes-my-suffix-dialog.component';
import { TypeClothesMySuffixDeletePopupComponent } from './type-clothes-my-suffix-delete-dialog.component';

@Injectable()
export class TypeClothesMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const typeClothesRoute: Routes = [
    {
        path: 'type-clothes-my-suffix',
        component: TypeClothesMySuffixComponent,
        resolve: {
            'pagingParams': TypeClothesMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypeClothes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-clothes-my-suffix/:id',
        component: TypeClothesMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypeClothes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeClothesPopupRoute: Routes = [
    {
        path: 'type-clothes-my-suffix-new',
        component: TypeClothesMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypeClothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-clothes-my-suffix/:id/edit',
        component: TypeClothesMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypeClothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-clothes-my-suffix/:id/delete',
        component: TypeClothesMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypeClothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
