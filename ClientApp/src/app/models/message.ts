import { User } from "./user";
import { Group } from "./group";

export class Message {
    constructor(
        public timestamp: any,
        public sender: User,
        public receivers: User[],
        public content: string,
        public group: Group
    ){}
}
