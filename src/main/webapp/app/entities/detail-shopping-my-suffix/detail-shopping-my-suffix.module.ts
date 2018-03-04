import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WearplusSharedModule } from '../../shared';
import {
    DetailShoppingMySuffixService,
    DetailShoppingMySuffixPopupService,
    DetailShoppingMySuffixComponent,
    DetailShoppingMySuffixDetailComponent,
    DetailShoppingMySuffixDialogComponent,
    DetailShoppingMySuffixPopupComponent,
    DetailShoppingMySuffixDeletePopupComponent,
    DetailShoppingMySuffixDeleteDialogComponent,
    detailShoppingRoute,
    detailShoppingPopupRoute,
    DetailShoppingMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detailShoppingRoute,
    ...detailShoppingPopupRoute,
];

@NgModule({
    imports: [
        WearplusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DetailShoppingMySuffixComponent,
        DetailShoppingMySuffixDetailComponent,
        DetailShoppingMySuffixDialogComponent,
        DetailShoppingMySuffixDeleteDialogComponent,
        DetailShoppingMySuffixPopupComponent,
        DetailShoppingMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        DetailShoppingMySuffixComponent,
        DetailShoppingMySuffixDialogComponent,
        DetailShoppingMySuffixPopupComponent,
        DetailShoppingMySuffixDeleteDialogComponent,
        DetailShoppingMySuffixDeletePopupComponent,
    ],
    providers: [
        DetailShoppingMySuffixService,
        DetailShoppingMySuffixPopupService,
        DetailShoppingMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WearplusDetailShoppingMySuffixModule {}
