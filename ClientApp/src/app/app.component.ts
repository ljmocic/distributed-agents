import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  messageContent = '';
  username = '';
  password = '';
  websocket: any;
  messages;

  constructor() {
    this.messages = [];
  }
  
  ngOnInit(): void {
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
      this.messages.push(event.data);
    };
    this.websocket.onerror = (event) => {
      console.log('error');
    };
  }

  sendMessage() {
    let command = {
      'type': 'MESSAGE',
      'message': this.messageContent,
      'username': this.username
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  login() {
    let command = {
      'type': 'LOGIN',
      'username': this.username,
      'password': this.password,
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  logout() {
    let command = {
      'type': 'LOGOUT',
      'username': this.username,
      'password': this.password,
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  createGroup() {
    let command = {
      'type': 'GROUP',
      'action': 'CREATE',
      'adminUsername': 'testUsername',
      'memberUsernames': ['testUsername'],
      'groupName': 'testGroup2'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  addMemberGroup() {
    let command = {
      'type': 'GROUP',
      'action': 'UPDATEMEMBERS',
      'memberUsernames': ['testUsername', 'testUsername123'],
      'groupName': 'testGroup2'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  addFriend() {
    let command = {
      'type': 'FRIEND',
      'action': 'ADDFRIEND',
      'friendToAdd': 'testUsername123'
    };
 
    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  removeFriend() {
    let command = {
      'type': 'FRIEND',
      'action': 'REMOVEFRIEND',
      'friendToRemove': 'testUsername123'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  sendMessageToUser() {
    let command = {
      'type': 'MESSAGE',
      'action': 'MESSAGEUSER',
      'userToMessage': 'testUsername123',
      'message': 'test'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  sendMessageToGroup() {
    let command = {
      'type': 'MESSAGE',
      'action': 'MESSAGEGROUP',
      'groupToMessage': 'testGroup2',
      'message': 'test'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  getFriends() {
    let command = {
      'type': 'FRIEND',
      'action': 'GETFRIENDS',
      'username': 'testUsername'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  getGroups() {
    let command = {
      'type': 'GROUP',
      'action': 'GETGROUPS',
      'username': 'testUsername'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  getMessages() {
    let command = {
      'type': 'MESSAGE',
      'action': 'GETUSERMESSAGES',
      'username': 'testUsername'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

  getMessagesFromGroup() {
    let command = {
      'type': 'MESSAGE',
      'action': 'GETGROUPMESSAGES',
      'groupToMessage': 'testGroup2'
    };

    this.websocket.send(JSON.stringify(command));
    this.messageContent = '';
  }

}