import { Injectable } from "@angular/core";
import { Group } from "../models/group";
import { User } from "../models/user";

@Injectable()
export class GroupService{

    applicationGroups: Group[];
    myGroups: Group[];
    currentGroup: Group;

    constructor() {
        this.applicationGroups = [];
        this.myGroups = [];
    }

    createNewGroup(name: string, admin: User) {
        let group = new Group(name, [ admin ], admin);
        this.applicationGroups.push(group);
        this.myGroups.push(group);
    }

    addMember(user: User){
        this.currentGroup.members.push(user);
    }

    removeMember(user: User){
        this.currentGroup.members.splice(this.currentGroup.members.indexOf(user), 1);
    }

    getGroup(name: string): Group{
        for(let i=0; i<this.applicationGroups.length; i++){
            if(this.applicationGroups[i].name === name){
                return this.applicationGroups[i];
            }
        }

        return null;
    }

    checkIfInGroup(user: User, group: Group): boolean{
        if(group.members.indexOf(user) !== -1){
            return true;
        }

        return false;
    }

    getGroupsOf(username: string): Group[]{
        let retVal = [];
        for(let i=0; i<this.applicationGroups.length; i++){
            for(let j=0; j<this.applicationGroups[i].members.length; j++){
                if(this.applicationGroups[i].members[j].username === username){
                    retVal.push(this.applicationGroups[i]);
                    break;
                }
            }
        }

        return retVal;
    }

}