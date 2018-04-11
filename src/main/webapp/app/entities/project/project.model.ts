import {BaseEntity, User} from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public budget?: number,
        public description?: string,
        public open?: boolean,
        public dateDeDebut?: any,
        public category?: BaseEntity,
        public user?:User,
    ) {
        this.open = false;
    }
}
