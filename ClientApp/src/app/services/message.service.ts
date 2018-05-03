import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { Message } from "../models/message";
import { Group } from "../models/group";
import { UserService } from "./user.service";
import { GroupService } from "./group.service";
import { WebSocketService } from "./web-socket.service";

@Injectable()
export class MessageService{
    
    public content: string;

    constructor(
        private groupService: GroupService,
        private userService: UserService,
        private webSocketService: WebSocketService
	){
    }

    renderMessages(messages: Message[]){
        let newContent = "";
        for(let i=0; i<messages.length; i++){
        newContent += "<b>" + messages[i].sender.username + " @ <i>" + messages[i].timestamp + "</i></b><br />";
        newContent += "<p>" + messages[i].content + "</p><br /><hr /><br />";
        }

        this.content = newContent;
    }

    sortByTimeStamp(messages: Message[]): Message[]{
        messages = messages.sort(this.compare);
        return messages;
    }

    compare(a,b): number {
        if (a.timestamp < b.timestamp)
          return -1;
        if (a.timestamp > b.timestamp)
          return 1;
        return 0;
    }

    sendMessage(content: string, sender: User){
        
		if(this.groupService.getCurrentGroup().members !== null && this.groupService.getCurrentGroup().members.length > 0){
            this.webSocketService.createMessageMessage
                (null, this.groupService.getCurrentGroup().name, content, 'MESSAGEGROUP', (data) => {
                    this.getMessagesFromGroup(this.groupService.getCurrentGroup());
                });
		}else if(this.userService.getFriend() !== null){
            this.webSocketService.createMessageMessage
                (this.userService.getFriend().username, null, content, 'MESSAGEUSER', (data) => {
                    this.getMessagesFromUserToUser(this.userService.getCurrentLoggedUser(), this.userService.getFriend());
                });
        }
    }

    getMessagesFromUserToUser(sender: User, receiver: User){
        let retValM = [];

        this.webSocketService.createMessageMessage
                (null, null, null, 'GETUSERMESSAGES', (data) => {
                retValM = this.sortByTimeStamp(JSON.parse(data));
                this.renderMessages(retValM);
        });
    }

    getMessagesFromGroup(group: Group){
        let retValM = [];
        this.webSocketService.createMessageMessage
            (null, null, null, 'GETGROUPMESSAGES', (data) => {
                retValM = this.sortByTimeStamp(JSON.parse(data));
                this.renderMessages(retValM);
        });
    }

}