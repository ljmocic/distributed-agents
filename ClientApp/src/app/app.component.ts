import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  messageContent = '';
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
    this.websocket.send(this.messageContent);
    this.messageContent = '';
  }
}