import { Injectable } from "@angular/core";

@Injectable()
export class WebSocketService{
    
    //messageContent = '';
    websocket: any;
    callbacks;

    constructor() {
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
          console.log(event.data)
          this.callbacks[0](event.data);
          this.callbacks.splice(0, 1);
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
            'type': 'GROUP',
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
            'memberNames': memberNames,
            'adminName': adminName
        };

        this.sendMessage(message, callback);

    }
}