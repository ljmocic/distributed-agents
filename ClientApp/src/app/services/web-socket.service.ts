import { Injectable } from "@angular/core";
import { Group } from "../models/group";
import { User } from "../models/user";
import { Message } from "../models/message";

@Injectable()
export class WebSocketService{
    
    //messageContent = '';
    websocket: any;
    callbacks: any;

    public currentGroup: Group;
    public myGroups: Group[];
    public content: string;

    public me: User;
    public friend: User;
    public applicationUsers: User[];
    public myFriends: User[];

    constructor() {
        this.applicationUsers = [];
        this.myGroups = [];
        this.myFriends = [];
        this.content = "";
        this.me = null;
        this.friend = null;
        this.currentGroup = null;

        this.callbacks = [];
        this.websocket = new WebSocket('ws://localhost:8080/ChatAppWeb/websocket');
        this.websocket.onopen = (event) => {
          console.log('open');
        };
        this.websocket.onclose = (event) => {
          console.log('close');
        };
        this.websocket.onmessage = (event) => {
          console.log('message');
          console.log(event.data);
          let parts = event.data.split(':');
          if(parts[0]==="REFRESH"){
              if(parts[1]==="users"){
                  this.updateEveryone();
              }else if(parts[1]==="groups"){
                    this.updateGroups();
              }else if(parts[1]==="messages"){
                    this.updateMessages();
              }
          }else{
            console.log(this.callbacks[0]);
            this.callbacks[0](event.data);
            this.callbacks.splice(0, 1);
          }
        };
        this.websocket.onerror = (event) => {
          console.log('error');
        };
    }

    sendMessage(message: any, callback) {
        this.callbacks.push(callback);
        this.websocket.send(JSON.stringify(message));
    }

    createUserMessage(username: string, password: string, type: string, callback: any){
        const message = {
            'type': type,
            'username': username,
            'password': password
        };

        this.sendMessage(message, callback);
    }

    createFriendMessage(friendToAdd: string, friendToRemove: string, action: string, callback: any){
        const message = {
            'type': 'FRIEND',
            'friendToAdd': friendToAdd,
            'friendToRemove': friendToRemove,
            'action': action
        };

        this.sendMessage(message, callback);
    }

    createMessageMessage(userToMessage: string, groupToMessage: string, content: string, action: string, callback: any){
        const message = {
            'type': 'MESSAGE',
            'action': action,
            'userToMessage': userToMessage,
            'groupToMessage': groupToMessage,
            'message': content
        };

        this.sendMessage(message, callback);
    }

    createGroupMessage(groupName: string, memberNames: String[], adminName: string,  action: string, callback: any){
        const message = {
            'type': 'GROUP',
            'action': action,
            'groupName': groupName,
            'memberUsernames': memberNames,
            'adminUsername': adminName
        };

        console.log(message);
        this.sendMessage(message, callback);

    }

    updateEveryone() {
        this.updateUsers();
        this.updateFriends();
    }

    updateUsers() {
        this.createUserMessage(null, null, "USERS", (data) => {
            console.log("Users synced");
            this.applicationUsers = JSON.parse(data);
        });
    }

    updateFriends() {
        console.log("getting my friends " + this.me.username);
        this.createFriendMessage(null, null, "GETFRIENDS", (data) => {
            this.myFriends = JSON.parse(data);
            if(this.friend !== null){
                for(let i=0; i<this.myFriends.length; i++){
                    if(this.myFriends[i].username === this.friend.username){
                        this.myFriends[i] = this.friend;
                        return;
                    }
                }
                this.friend = null;
            }
        });
    }

    updateGroups() {
        this.createGroupMessage(null, [], this.me.username, 'GETGROUPSOFUSER', (data) => {
            console.log('synced groups');
            this.myGroups = JSON.parse(data);
        });
    }

    updateMessages() {
        if(this.currentGroup === null && this.friend !== null){
            let retValM = [];

            this.createMessageMessage
                    (this.friend.username, null, null, 'GETUSERMESSAGES', (data) => {
                    retValM = this.sortByTimeStamp(JSON.parse(data));
                    this.renderMessages(retValM);
            });
        }else if(this.currentGroup !== null && this.friend === null){
            let retValM = [];
            this.createMessageMessage
                (null, this.currentGroup.name, null, 'GETGROUPMESSAGES', (data) => {
                    retValM = this.sortByTimeStamp(JSON.parse(data));
                    this.renderMessages(retValM);
            });
        }
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

}