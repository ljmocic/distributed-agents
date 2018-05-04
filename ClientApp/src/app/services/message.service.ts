import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { Message } from "../models/message";
import { Group } from "../models/group";
import { UserService } from "./user.service";
import { GroupService } from "./group.service";
import { WebSocketService } from "./web-socket.service";

@Injectable()
export class MessageService{

    constructor(
        private groupService: GroupService,
        private userService: UserService,
        public webSocketService: WebSocketService
	){
    }

    sendMessage(content: string, sender: User){
        
		if(this.webSocketService.currentGroup !== null && this.webSocketService.currentGroup.members !== null && this.webSocketService.currentGroup.members.length > 0){
            this.webSocketService.createMessageMessage
                (null, this.webSocketService.currentGroup.name, content, 'MESSAGEGROUP', (data) => {
                    //this.getMessages();
                });
		}else if(this.webSocketService.friend !== null){
            this.webSocketService.createMessageMessage
                (this.webSocketService.friend.username, null, content, 'MESSAGEUSER', (data) => {
                    //this.getMessages();
                });
        }
    }

    getMessages(){
        this.webSocketService.updateMessages();
    }

}