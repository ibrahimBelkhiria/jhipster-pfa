import { BaseEntity } from './../../shared';

export class Prestataire implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public imageContentType?: string,
        public image?: any,
        public description?: string,
        public email?: string,
        public category?: BaseEntity,
    ) {
    }
}
