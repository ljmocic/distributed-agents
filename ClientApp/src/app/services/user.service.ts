import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { GroupService } from "./group.service";
import { WebSocketService } from "./web-socket.service";
import { Router } from "@angular/router";

@Injectable()
export class UserService {

    constructor(
        private groupService: GroupService,
        public webSocketService: WebSocketService,
        private router: Router) {
    }

    loginUser(user: User){
        this.webSocketService.createUserMessage(user.username, user.password, 'LOGIN', (data) => {
            if(data === "Logged in!"){
                this.webSocketService.createUserMessage(user.username, null, "USER", (data) => {
                    this.webSocketService.me = JSON.parse(data);
                    this.webSocketService.updateEveryone();
                    this.groupService.setCurrentGroup("");
                    this.webSocketService.updateGroups();
                    this.router.navigate(["/messages/friends"]);
                });
            }else{
                this.webSocketService.me = null;
            }
        });
    }


    logoutUser(){
        this.webSocketService.createUserMessage(this.webSocketService.me.username, this.webSocketService.me.password, 'LOGOUT', (data) => {
           this.webSocketService.me = null;
            this.webSocketService.myFriends = [];
            this.webSocketService.applicationUsers = [];
            this.webSocketService.friend = null;
            this.groupService.setCurrentGroup("");
            this.webSocketService.myGroups = [];
            this.router.navigate(["./"]);
        });
    }

    createUser(user: User){
        this.webSocketService.createUserMessage(user.username, user.password, 'REGISTER', (data) => {
            this.webSocketService.me = JSON.parse(data);
            this.webSocketService.myGroups = [];
            this.groupService.setCurrentGroup("");
            this.webSocketService.updateUsers();
            this.router.navigate(["/messages"]);
        });
    }

    addFriend(user: User) {
        this.webSocketService.createFriendMessage(user.username, null, 'ADDFRIEND',(data) => {
            console.log("friend added");
            //this.webSocketService.updateEveryone();
        });
    }

    removeFriend(user: User) {
        this.webSocketService.createFriendMessage(null, user.username, 'REMOVEFRIEND', (data) => {
            console.log("friend removed");
            //this.webSocketService.updateEveryone();
        });
    }

    checkIfMyFriend(user: User): boolean{
        for(let i=0; i<this.webSocketService.myFriends.length; i++){
            if(this.webSocketService.myFriends[i].username === user.username){
                return true;
            }
        }
        return false;
    }

    getUser(username: string): User{
        for(let i=0; i< this.webSocketService.applicationUsers.length; i++){
            if(this.webSocketService.applicationUsers[i].username === username){
                return this.webSocketService.applicationUsers[i];
            }
        }

        return null;
    }

	/*getCurrentLoggedUser(): User{
		return this.updateService.me;
	}*/
	
	setCurrentLoggedUser(username: string) {
		this.webSocketService.me = this.getUser(username);
		this.groupService.setCurrentGroup("");
	}
	
	/*getFriend(): User{
		return this.updateService.friend;
	}*/
	
	setFriend(username: string){
		this.webSocketService.friend = this.getUser(username);
		this.groupService.setCurrentGroup("");
	}
    
    setCurrentGroup(name: string){
        this.setFriend("");
        this.groupService.setCurrentGroup(name);
    }
}