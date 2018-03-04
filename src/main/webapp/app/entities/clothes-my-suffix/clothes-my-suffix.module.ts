import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WearplusSharedModule } from '../../shared';
import {
    ClothesMySuffixService,
    ClothesMySuffixPopupService,
    ClothesMySuffixComponent,
    ClothesMySuffixDetailComponent,
    ClothesMySuffixDialogComponent,
    ClothesMySuffixPopupComponent,
    ClothesMySuffixDeletePopupComponent,
    ClothesMySuffixDeleteDialogComponent,
    clothesRoute,
    clothesPopupRoute,
    ClothesMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clothesRoute,
    ...clothesPopupRoute,
];

@NgModule({
    imports: [
        WearplusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClothesMySuffixComponent,
        ClothesMySuffixDetailComponent,
        ClothesMySuffixDialogComponent,
        ClothesMySuffixDeleteDialogComponent,
        ClothesMySuffixPopupComponent,
        ClothesMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ClothesMySuffixComponent,
        ClothesMySuffixDialogComponent,
        ClothesMySuffixPopupComponent,
        ClothesMySuffixDeleteDialogComponent,
        ClothesMySuffixDeletePopupComponent,
    ],
    providers: [
        ClothesMySuffixService,
        ClothesMySuffixPopupService,
        ClothesMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WearplusClothesMySuffixModule {}
