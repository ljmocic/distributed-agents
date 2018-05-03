import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { GroupService } from "./group.service";
import { WebSocketService } from "./web-socket.service";
import { Router } from "@angular/router";

@Injectable()
export class UserService {

    public me: User;
    private friend: User;
    public applicationUsers: User[];
    public myFriends: User[];

    constructor(
        private groupService: GroupService,
        private webSocketService: WebSocketService,
        private router: Router) {
		this.friend = null;
        this.me = null;
        this.applicationUsers=[];
        this.myFriends=[];
    }

    loginUser(user: User){
        this.webSocketService.createUserMessage(user.username, user.password, 'LOGIN', (data) => {
            if(data === "Logged in!"){
                this.webSocketService.createUserMessage(user.username, null, "USER", (data) => {
                    this.me = JSON.parse(data);
                    this.getMyFriends();
                    this.getAllUsers();
                    this.groupService.setCurrentGroup("");
                    this.groupService.loadUserGroups(JSON.parse(data).username);
                    this.router.navigate(["/messages/friends"]);
                });
            }else{
                this.me = null;
            }
        });
    }


    logoutUser(){
        this.webSocketService.createUserMessage(this.me.username, this.me.password, 'LOGOUT', (data) => {
           this.me = null;
            this.myFriends = [];
            this.applicationUsers = [];
            this.friend = null;
            this.groupService.setCurrentGroup("");
            this.groupService.myGroups = [];
            this.router.navigate(["./"]);
        });
    }

    getMyFriends() {
        alert("getting my friends " + this.me.username);
        this.webSocketService.createFriendMessage(null, null, "GETFRIENDS", (data) => {
            this.myFriends = JSON.parse(data);
        });
    }

    createUser(user: User){
        this.webSocketService.createUserMessage(user.username, user.password, 'REGISTER', (data) => {
            this.me = JSON.parse(data);
            this.groupService.myGroups = [];
            this.groupService.setCurrentGroup("");
            this.getAllUsers();
            this.router.navigate(["/messages"]);
        });
    }

    addFriend(user: User) {
        this.webSocketService.createFriendMessage(user.username, null, 'ADDFRIEND',(data) => {
            alert("friend added");
            this.webSocketService.createFriendMessage(null, null,'GETFRIENDS', (data) => {
                alert("friends synchronized");
                this.myFriends = JSON.parse(data);
                this.getAllUsers();
            });
        });
    }

    removeFriend(user: User) {
        this.webSocketService.createFriendMessage(null, user.username, 'REMOVEFRIEND', (data) => {
            alert("friend removed");
            this.webSocketService.createFriendMessage(null, null,'GETFRIENDS', (data) => {
                alert("friends synchronized");
                this.myFriends = JSON.parse(data);
                this.getAllUsers();
            });
        });
    }

    checkIfMyFriend(user: User): boolean{
        for(let i=0; i<this.myFriends.length; i++){
            if(this.myFriends[i].username === user.username){
                return true;
            }
        }
        return false;
    }

    getUser(username: string): User{
        for(let i=0; i< this.applicationUsers.length; i++){
            if(this.applicationUsers[i].username === username){
                return this.applicationUsers[i];
            }
        }

        return null;
    }

	getCurrentLoggedUser(): User{
		return this.me;
	}
	
	setCurrentLoggedUser(username: string) {
		this.me = this.getUser(username);
		this.groupService.setCurrentGroup("");
	}
	
	getFriend(): User{
		return this.friend;
	}
	
	setFriend(username: string){
		this.friend = this.getUser(username);
		this.groupService.setCurrentGroup("");
	}
	
    getAllUsers(){
        alert("getting all users");
        this.webSocketService.createUserMessage(null, null, "USERS", (data) => {
            alert("Users synced");
            this.applicationUsers = JSON.parse(data);
        });
    }
    
    setCurrentGroup(name: string){
        this.setFriend("");
        this.groupService.setCurrentGroup(name);
    }
}