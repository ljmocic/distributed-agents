import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { GroupService } from "./group.service";

@Injectable()
export class UserService {

    public applicationUsers: User[];
    public myFriends: User[];
    public me: User;

    constructor(
        private groupService: GroupService) {
        this.applicationUsers = [];
        this.myFriends = [];
    }

    loginUser(user: User){
        this.me = this.getUser(user.username);
        this.myFriends = this.getFriendsOf(this.me.username);
        this.groupService.myGroups = this.groupService.getGroupsOf(this.me.username);
    }

    logoutUser(){
        this.me = null;
    }

    createUser(user: User){
        this.applicationUsers.push(user);
        this.me = user;
        this.myFriends = this.getFriendsOf(this.me.username);
    }

    addFriend(user: User) {
        if(this.myFriends.indexOf(user) === -1){
            this.me.friends.push(user);
            this.myFriends.push(user);
            for(let i=0; i<this.applicationUsers.length; i++){
                if(this.applicationUsers[i].username === this.me.username){
                    this.applicationUsers[i] = this.me;
                    break;
                }
            }
        }
    }

    removeFriend(user: User) {
        if(this.myFriends.indexOf(user) > -1){
            this.me.friends.splice(this.me.friends.indexOf(user), 1);
            this.myFriends.splice(this.myFriends.indexOf(user), 1);
            for(let i=0; i<this.applicationUsers.length; i++){
                if(this.applicationUsers[i].username === this.me.username){
                    this.applicationUsers[i] = this.me;
                    break;
                }
            }
        }
    }

    checkIfMyFriend(user: User): boolean{
        for(let i=0; i<this.myFriends.length; i++) {
            if(this.myFriends[i].username === user.username) {
              return true;
            }
        }
      
        return false;
    }

    getUser(username: string): User{
        for(let i=0; i<this.applicationUsers.length; i++){
            if(this.applicationUsers[i].username === username){
                return this.applicationUsers[i];
            }
        }

        return null;
    }

    getFriendsOf(username: string): User[]{
        let retVal = [];
        for(let i=0; i<this.applicationUsers.length; i++){
            if(this.applicationUsers[i].username === username){
                for(let j=0; j<this.applicationUsers[i].friends.length; j++){
                    retVal.push(this.applicationUsers[i].friends[j]);
                }
            }else{
                for(let j=0; j<this.applicationUsers[i].friends.length; j++){
                    if(this.applicationUsers[i].friends[j].username === username){
                        retVal.push(this.applicationUsers[i]);
                    }
                }
            }
        }

        return retVal;
    }

}