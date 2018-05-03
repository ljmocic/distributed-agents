import { User } from "./user";

export class Message {
    constructor(
        public timestamp: any,
        public sender: User,
        public receivers: User[],
        public content: string
    ){}
}
