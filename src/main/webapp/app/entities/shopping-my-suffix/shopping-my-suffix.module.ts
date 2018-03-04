import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WearplusSharedModule } from '../../shared';
import {
    ShoppingMySuffixService,
    ShoppingMySuffixPopupService,
    ShoppingMySuffixComponent,
    ShoppingMySuffixDetailComponent,
    ShoppingMySuffixDialogComponent,
    ShoppingMySuffixPopupComponent,
    ShoppingMySuffixDeletePopupComponent,
    ShoppingMySuffixDeleteDialogComponent,
    shoppingRoute,
    shoppingPopupRoute,
    ShoppingMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shoppingRoute,
    ...shoppingPopupRoute,
];

@NgModule({
    imports: [
        WearplusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShoppingMySuffixComponent,
        ShoppingMySuffixDetailComponent,
        ShoppingMySuffixDialogComponent,
        ShoppingMySuffixDeleteDialogComponent,
        ShoppingMySuffixPopupComponent,
        ShoppingMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingMySuffixComponent,
        ShoppingMySuffixDialogComponent,
        ShoppingMySuffixPopupComponent,
        ShoppingMySuffixDeleteDialogComponent,
        ShoppingMySuffixDeletePopupComponent,
    ],
    providers: [
        ShoppingMySuffixService,
        ShoppingMySuffixPopupService,
        ShoppingMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WearplusShoppingMySuffixModule {}
