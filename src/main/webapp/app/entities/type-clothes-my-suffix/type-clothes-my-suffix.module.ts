import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WearplusSharedModule } from '../../shared';
import {
    TypeClothesMySuffixService,
    TypeClothesMySuffixPopupService,
    TypeClothesMySuffixComponent,
    TypeClothesMySuffixDetailComponent,
    TypeClothesMySuffixDialogComponent,
    TypeClothesMySuffixPopupComponent,
    TypeClothesMySuffixDeletePopupComponent,
    TypeClothesMySuffixDeleteDialogComponent,
    typeClothesRoute,
    typeClothesPopupRoute,
    TypeClothesMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeClothesRoute,
    ...typeClothesPopupRoute,
];

@NgModule({
    imports: [
        WearplusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeClothesMySuffixComponent,
        TypeClothesMySuffixDetailComponent,
        TypeClothesMySuffixDialogComponent,
        TypeClothesMySuffixDeleteDialogComponent,
        TypeClothesMySuffixPopupComponent,
        TypeClothesMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        TypeClothesMySuffixComponent,
        TypeClothesMySuffixDialogComponent,
        TypeClothesMySuffixPopupComponent,
        TypeClothesMySuffixDeleteDialogComponent,
        TypeClothesMySuffixDeletePopupComponent,
    ],
    providers: [
        TypeClothesMySuffixService,
        TypeClothesMySuffixPopupService,
        TypeClothesMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WearplusTypeClothesMySuffixModule {}
