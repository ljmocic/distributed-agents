import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { Group } from '../../models/group';
import { UserService } from '../../services/user.service';
import { MessageService } from '../../services/message.service';
import { GroupService } from '../../services/group.service';

@Component({
  selector: 'app-chat-online',
  templateUrl: './chat-online.component.html',
  styleUrls: ['./chat-online.component.css']
})
export class ChatOnlineComponent implements OnInit {

  viewFriends: User[];
  viewGroups: Group[];
  query: string;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private groupService: GroupService
  ) { }

  ngOnInit() {
    this.query = "search for friends ...";
    this.viewFriends = this.userService.myFriends;
    this.viewGroups = this.groupService.myGroups;
  }

  searchFor(){
    this.viewFriends = [];
    for (let i=0; i<this.userService.myFriends.length; i++) {
      if (this.userService.myFriends[i].username.indexOf(this.query) > -1) {
        this.viewFriends.push(this.userService.myFriends[i]);
      }
    }
    this.viewGroups = [];
    for (let i=0; i<this.groupService.myGroups.length; i++) {
      if (this.groupService.myGroups[i].name.indexOf(this.query) > -1) {
        this.viewGroups.push(this.groupService.myGroups[i]);
      }
    }
  }

  getMessagesFromUser(username: string){
    alert("Daj poruke od "+username);
    this.messageService.receivers = [ this.userService.getUser(username) ];
    this.groupService.currentGroup = null;
  }

  getMessagesFromGroup(group: string){
    alert("Daj poruke od "+group);
    this.groupService.currentGroup = this.groupService.getGroup(group);
    this.messageService.receivers = this.groupService.currentGroup.members;
  }

}
