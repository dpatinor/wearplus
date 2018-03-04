import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DetailShoppingMySuffixComponent } from './detail-shopping-my-suffix.component';
import { DetailShoppingMySuffixDetailComponent } from './detail-shopping-my-suffix-detail.component';
import { DetailShoppingMySuffixPopupComponent } from './detail-shopping-my-suffix-dialog.component';
import { DetailShoppingMySuffixDeletePopupComponent } from './detail-shopping-my-suffix-delete-dialog.component';

@Injectable()
export class DetailShoppingMySuffixResolvePagingParams implements Resolve<any> {

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

export const detailShoppingRoute: Routes = [
    {
        path: 'detail-shopping-my-suffix',
        component: DetailShoppingMySuffixComponent,
        resolve: {
            'pagingParams': DetailShoppingMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetailShoppings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'detail-shopping-my-suffix/:id',
        component: DetailShoppingMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetailShoppings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailShoppingPopupRoute: Routes = [
    {
        path: 'detail-shopping-my-suffix-new',
        component: DetailShoppingMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetailShoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-shopping-my-suffix/:id/edit',
        component: DetailShoppingMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetailShoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-shopping-my-suffix/:id/delete',
        component: DetailShoppingMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetailShoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
