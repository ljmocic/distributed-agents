import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { Message } from "../models/message";
import { Group } from "../models/group";

@Injectable()
export class MessageService{

    receivers: User[];
    messages: Message[];

    constructor(){
        this.receivers = [];
        this.messages = [];
    }

    sendMessage(content: string, sender: User){
        let message = new Message(Date.now(), sender, this.receivers, content);
        alert("[" + message.timestamp + "] Saljem poruku sadrzaja: "+message.content);
        this.messages.push(message);
    }

    getMessagesFromUserToUser(sender: User, receiver: User): Message[]{
        let retValM = [];
        for(let i=0; i<this.messages.length; i++){
            if(this.messages[i].sender.username === sender.username){
                if(this.messages[i].receivers.length === 1 && this.messages[i].receivers[0].username === receiver.username){
                    retValM.push(this.messages[i]);
                }
            }
            if(this.messages[i].sender.username === receiver.username){
                if(this.messages[i].receivers.length === 1 && this.messages[i].receivers[0].username === sender.username){
                    retValM.push(this.messages[i]);
                }
            }
        }

        return this.sortByTimeStamp(retValM);
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

    getMessagesFromGroup(group: Group): Message[]{
        let retValM = [];
        for(let i=0; i<this.messages.length; i++){
            let messRec = this.messages[i].receivers;
            messRec.push(this.messages[i].sender);

            let maxflag = false;
            for(let j=0; j<group.members.length; j++){
                let flag = false;
                for(let k=0; k<messRec.length; k++){
                    if(group.members[j].username === messRec[k].username){
                        flag = true;
                        break;
                    }
                }

                if(flag === false){
                    maxflag = true;
                    break;
                }
            }

            if(maxflag === false){
                retValM.push(this.messages[i]);
            }
        }

        return retValM;
    }

}