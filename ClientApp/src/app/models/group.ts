import { User } from "./user";

export class Group {
    constructor(
        public name: string,
        public members: User[],
        public admin?: User
    ){}
}
