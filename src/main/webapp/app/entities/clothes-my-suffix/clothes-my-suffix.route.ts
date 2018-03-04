import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ClothesMySuffixComponent } from './clothes-my-suffix.component';
import { ClothesMySuffixDetailComponent } from './clothes-my-suffix-detail.component';
import { ClothesMySuffixPopupComponent } from './clothes-my-suffix-dialog.component';
import { ClothesMySuffixDeletePopupComponent } from './clothes-my-suffix-delete-dialog.component';

@Injectable()
export class ClothesMySuffixResolvePagingParams implements Resolve<any> {

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

export const clothesRoute: Routes = [
    {
        path: 'clothes-my-suffix',
        component: ClothesMySuffixComponent,
        resolve: {
            'pagingParams': ClothesMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clothes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clothes-my-suffix/:id',
        component: ClothesMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clothes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clothesPopupRoute: Routes = [
    {
        path: 'clothes-my-suffix-new',
        component: ClothesMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clothes-my-suffix/:id/edit',
        component: ClothesMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clothes-my-suffix/:id/delete',
        component: ClothesMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clothes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
