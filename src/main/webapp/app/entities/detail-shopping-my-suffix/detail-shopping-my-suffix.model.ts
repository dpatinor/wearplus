import { BaseEntity } from './../../shared';

export class DetailShoppingMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public quantity?: number,
        public valor?: number,
        public shoppingId?: number,
        public clothesId?: number,
    ) {
    }
}
