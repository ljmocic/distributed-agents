import { Injectable } from "@angular/core";
import { Group } from "../models/group";
import { User } from "../models/user";
import { UserService } from "./user.service";
import { WebSocketService } from "./web-socket.service";

@Injectable()
export class GroupService{

    constructor(
        public webSocketService: WebSocketService
	) {
    }

    createNewGroup(name: string, admin: User) {
        this.webSocketService.createGroupMessage(name, [admin.username], admin.username, 'CREATE', (data) => {
            console.log('group created');
            //this.webSocketService.updateGroups();
        });
    }

    addMember(user: User){
        if(!this.checkIfInGroup(user.username, this.webSocketService.currentGroup.name)){
            this.webSocketService.currentGroup.members.push(user);
            let names = [];
            for(let i=0; i<this.webSocketService.currentGroup.members.length; i++){
                names.push(this.webSocketService.currentGroup.members[i].username);
            }

            this.webSocketService.createGroupMessage(this.webSocketService.currentGroup.name, names, this.webSocketService.currentGroup.admin.username, 'UPDATEMEMBERS', (data) => {
                //this.webSocketService.updateGroups();
            });
        }
    }

    removeMember(user: User){
        if(this.checkIfInGroup(user.username, this.webSocketService.currentGroup.name)){
            this.webSocketService.currentGroup.members.splice(this.findIndexOfUser(user.username), 1);
            let names = [];
            for(let i=0; i<this.webSocketService.currentGroup.members.length; i++){
                names.push(this.webSocketService.currentGroup.members[i].username);
            }

            this.webSocketService.createGroupMessage(this.webSocketService.currentGroup.name, names, this.webSocketService.currentGroup.admin.username, 'UPDATEMEMBERS', (data) => {
                //this.webSocketService.updateGroups();
            });
        }
	}

    getGroup(name: string): Group{
        for(let i=0; i<this.webSocketService.myGroups.length; i++){
            if(this.webSocketService.myGroups[i].name === name){
                return this.webSocketService.myGroups[i];
            }
        }

        return null;
    }

    checkIfInGroup(user: string, group: string): boolean{
        const vv = this.getGroup(group);
        if(vv !== null){
            for(let i=0; i<vv.members.length; i++){
                if(vv.members[i].username === user){
                    return true;
                }
            }
        }

        return false;
    }

    findIndexOfUser(username: string): number{
        for(let i=0; i<this.webSocketService.currentGroup.members.length; i++){
            if(this.webSocketService.currentGroup.members[i].username === username){
                return i;
            }
        }

        return -1;
    }
	
	/*getCurrentGroup(): Group{
		return this.updateService.currentGroup;
	}*/

	setCurrentGroup(name: string){
        alert("setting curr group "+name);
        if(name === "" || name === null){
            this.webSocketService.currentGroup = null;
        }else{
		    this.webSocketService.currentGroup = this.getGroup(name);
        }
	}
}