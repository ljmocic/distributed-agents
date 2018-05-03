export class User {
    
    friends: any;

    constructor(
        public username: string,
        public password: string,
        public firstName?: string,
        public lastName?: string
    ){
        this.friends = [];
    }

}
