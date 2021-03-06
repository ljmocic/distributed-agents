import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { Group } from '../../models/group';
import { UserService } from '../../services/user.service';
import { MessageService } from '../../services/message.service';
import { GroupService } from '../../services/group.service';
import { Message } from '../../models/message';

@Component({
  selector: 'app-chat-messages',
  templateUrl: './chat-messages.component.html',
  styleUrls: ['./chat-messages.component.css']
})
export class ChatMessagesComponent implements OnInit {

  minusOpen: boolean;
  plusOpen: boolean;
  content: string;

  constructor(
    private userService: UserService,
    private groupService: GroupService,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.content = "";
    this.minusOpen = false;
    this.plusOpen = false;
  }

  getTitle(): String{
    if (this.groupService.webSocketService.currentGroup !== null && this.userService.webSocketService.friend === null){
      return this.groupService.webSocketService.currentGroup.name;
    }else if (this.groupService.webSocketService.currentGroup === null && this.userService.webSocketService.friend !== null){
      return this.userService.webSocketService.friend.username;
    }

    return "";
  }

  getMessages() {
    this.messageService.getMessages();
  }

  openDropdownPlus() {
    this.plusOpen = !this.plusOpen;
  }

  openDropdownMinus() {
    this.minusOpen = !this.minusOpen;
  }

  addUser(user: User){
    this.groupService.addMember(user);
    this.plusOpen = false;
  }

  removeUser(user: User){
    this.groupService.removeMember(user);
    this.minusOpen = false;
  }

  checkAuthority(){
    if(this.groupService.webSocketService.currentGroup === null){
      return false;
    }

    if(this.groupService.webSocketService.currentGroup.admin === null || this.groupService.webSocketService.currentGroup.admin === undefined){
      return false;
    }

    if(this.userService.webSocketService.me !== null){
      return this.groupService.webSocketService.currentGroup.admin.username === this.userService.webSocketService.me.username;
    }

    return false;
  }

}
