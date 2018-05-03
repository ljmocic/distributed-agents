import { Component, OnInit } from '@angular/core';
import { Message } from '../../models/message';
import { UserService } from '../../services/user.service';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-chat-message',
  templateUrl: './chat-message.component.html',
  styleUrls: ['./chat-message.component.css']
})
export class ChatMessageComponent implements OnInit {

  content: string

  constructor(
    private userService: UserService,
    private messageService: MessageService
  ) { 
  }

  ngOnInit() {
    this.content = "";
  }

  send() {

    if(this.content === "") {
      if (confirm('Are you sure you want to send an empty message?')) {
        // continue
      } else {
          return;
      }
    }

    this.messageService.sendMessage(this.content, this.userService.getCurrentLoggedUser());
    this.content = "";
  }

}
