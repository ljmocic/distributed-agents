import { Injectable } from "@angular/core";
import { Group } from "../models/group";
import { User } from "../models/user";
import { UserService } from "./user.service";
import { WebSocketService } from "./web-socket.service";

@Injectable()
export class GroupService{

    public currentGroup: Group;
    public myGroups: Group[];

    constructor(
        private webSocketService: WebSocketService
	) {
        this.myGroups = [];
        this.currentGroup=null;
    }

    createNewGroup(name: string, admin: User) {
        this.webSocketService.createGroupMessage(name, [admin.username], admin.username, 'CREATE', (data) => {
            this.webSocketService.createGroupMessage(null, [], admin.username, 'GETGROUPSOFUSER', (data) => {
                this.myGroups = JSON.parse(data);
            });
        });
    }

    addMember(user: User){
        if(!this.checkIfInGroup(user.username, this.currentGroup.name)){
            this.currentGroup.members.push(user);
            let names = [];
            for(let i=0; i<this.currentGroup.members.length; i++){
                names.push(this.currentGroup.members[i].username);
            }

            this.webSocketService.createGroupMessage(this.currentGroup.name, names, this.currentGroup.admin.username, 'UPDATEMEMBERS', (data) => {
                this.loadUserGroups(this.currentGroup.admin.username);
            });
        }
    }

    removeMember(user: User){
        if(this.checkIfInGroup(user.username, this.currentGroup.name)){
            this.currentGroup.members.splice(this.findIndexOfUser(user.username), 1);
            let names = [];
            for(let i=0; i<this.currentGroup.members.length; i++){
                names.push(this.currentGroup.members[i].username);
            }

            this.webSocketService.createGroupMessage(this.currentGroup.name, names, this.currentGroup.admin.username, 'UPDATEMEMBERS', (data) => {
                this.loadUserGroups(this.currentGroup.admin.username);
            });
        }
	}

    loadUserGroups(username: string){
        alert("loading groups for: "+username);
        this.webSocketService.createGroupMessage(null, [], username, 'GETGROUPSOFUSER', (data) => {
            this.myGroups = JSON.parse(data);
        });
    }

    getGroup(name: string): Group{
        for(let i=0; i<this.myGroups.length; i++){
            if(this.myGroups[i].name === name){
                return this.myGroups[i];
            }
        }

        return null;
    }

    checkIfInGroup(user: string, group: string): boolean{
        if(this.getGroup(group) !== null){
            return true;
        }

        return false;
    }

    findIndexOfUser(username: string): number{
        for(let i=0; i<this.currentGroup.members.length; i++){
            if(this.currentGroup.members[i].username === username){
                return i;
            }
        }

        return -1;
    }
	
	getCurrentGroup(): Group{
		return this.currentGroup;
	}
	
	setCurrentGroup(name: string){
        alert("setting curr group "+name);
        if(name === "" || name === null){
            this.currentGroup = null;
        }else{
		    this.currentGroup = this.getGroup(name);
        }
	}
}