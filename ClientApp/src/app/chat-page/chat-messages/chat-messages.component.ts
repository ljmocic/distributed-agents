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

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private groupService: GroupService
  ) { }

  ngOnInit() {
    this.minusOpen = false;
    this.plusOpen = false;
  }

  getTitle(): String{
    if(this.messageService.receivers.length > 0 )
      return this.messageService.receivers[0].username+" ("+this.messageService.receivers[0].firstName+" "+this.messageService.receivers[0].lastName+")";
    else
      return "";
  }

  getMessages(): Message[]{
    let messages = [];
    if(this.groupService.currentGroup === null)
      messages = this.messageService.getMessagesFromUserToUser(this.userService.me, this.messageService.receivers[0]);
    else
      messages = this.messageService.getMessagesFromGroup(this.groupService.currentGroup);
    return messages;
  }

  renderMessages(): String{
    const messages = this.getMessages();
    let newContent = "";
    for(let i=0; i<messages.length; i++){
      newContent += "<b>" + messages[i].sender.username + " @ <i>" + messages[i].timestamp + "</i></b><br />";
      newContent += "<p>" + messages[i].content + "</p><br /><hr /><br />";
    }

    return newContent;
  }

  openDropdownPlus() {
    this.plusOpen = !this.plusOpen;
  }

  openDropdownMinus() {
    this.minusOpen = !this.minusOpen;
  }

  addUser(user: User){
    this.groupService.addMember(user);
    this.messageService.receivers = this.groupService.currentGroup.members;
    this.plusOpen = false;
  }

  removeUser(user: User){
    this.groupService.removeMember(user);
    this.messageService.receivers = this.groupService.currentGroup.members;
    this.minusOpen = false;
  }

  checkAuthority(){
    if(this.groupService.currentGroup === null){
      return false;
    }
    return this.groupService.currentGroup.admin.username === this.userService.me.username;
  }

}
