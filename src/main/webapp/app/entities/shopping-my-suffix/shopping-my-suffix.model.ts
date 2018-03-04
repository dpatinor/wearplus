import { BaseEntity } from './../../shared';

export class ShoppingMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public idType?: string,
        public idNumber?: number,
        public dateOrder?: any,
        public dateDelivery?: any,
        public address?: string,
        public total?: number,
    ) {
    }
}
