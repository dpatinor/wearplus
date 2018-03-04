import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShoppingMySuffixComponent } from './shopping-my-suffix.component';
import { ShoppingMySuffixDetailComponent } from './shopping-my-suffix-detail.component';
import { ShoppingMySuffixPopupComponent } from './shopping-my-suffix-dialog.component';
import { ShoppingMySuffixDeletePopupComponent } from './shopping-my-suffix-delete-dialog.component';

@Injectable()
export class ShoppingMySuffixResolvePagingParams implements Resolve<any> {

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

export const shoppingRoute: Routes = [
    {
        path: 'shopping-my-suffix',
        component: ShoppingMySuffixComponent,
        resolve: {
            'pagingParams': ShoppingMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shoppings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shopping-my-suffix/:id',
        component: ShoppingMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shoppings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shoppingPopupRoute: Routes = [
    {
        path: 'shopping-my-suffix-new',
        component: ShoppingMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-my-suffix/:id/edit',
        component: ShoppingMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-my-suffix/:id/delete',
        component: ShoppingMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shoppings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
