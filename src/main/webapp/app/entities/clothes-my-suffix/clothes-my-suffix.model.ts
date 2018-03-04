import { BaseEntity } from './../../shared';

export class ClothesMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
        public value?: number,
        public image?: string,
        public typeClothesId?: number,
    ) {
    }
}
