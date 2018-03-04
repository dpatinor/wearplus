import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WearplusTypeClothesMySuffixModule } from './type-clothes-my-suffix/type-clothes-my-suffix.module';
import { WearplusClothesMySuffixModule } from './clothes-my-suffix/clothes-my-suffix.module';
import { WearplusShoppingMySuffixModule } from './shopping-my-suffix/shopping-my-suffix.module';
import { WearplusDetailShoppingMySuffixModule } from './detail-shopping-my-suffix/detail-shopping-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WearplusTypeClothesMySuffixModule,
        WearplusClothesMySuffixModule,
        WearplusShoppingMySuffixModule,
        WearplusDetailShoppingMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WearplusEntityModule {}
