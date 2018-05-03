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
    if (this.groupService.getCurrentGroup() !== null && this.userService.getFriend() === null){
      return this.groupService.getCurrentGroup().name;
    }else if (this.groupService.getCurrentGroup() === null && this.userService.getFriend() !== null){
      return this.userService.getFriend().username;
    }

    return "";
  }

  getMessages() {
    if(this.groupService.getCurrentGroup() !== null && this.userService.getFriend() === null){
        this.messageService.getMessagesFromGroup(this.groupService.getCurrentGroup());
    }else if (this.groupService.getCurrentGroup() === null && this.userService.getFriend() !== null){
        this.messageService.getMessagesFromUserToUser(this.userService.getCurrentLoggedUser(), this.userService.getFriend());
    }
  
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
    if(this.groupService.getCurrentGroup() === null){
      return false;
    }
    return this.groupService.getCurrentGroup().admin.username === this.userService.getCurrentLoggedUser().username;
  }

}
