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
    private groupService: GroupService
  ) { }

  ngOnInit() {
    this.query = "search for friends ...";
    this.updateData();
  }

  updateData(){
    this.viewFriends = this.userService.myFriends;
    this.viewGroups = this.groupService.myGroups;
  }

  searchFor(){
    this.viewFriends = [];
    let myFriends = this.userService.myFriends;
    for (let i=0; i<myFriends.length; i++) {
      if (myFriends[i].username.indexOf(this.query) > -1) {
        this.viewFriends.push(myFriends[i]);
      }
    }
    this.viewGroups = [];
    let myGroups = this.groupService.myGroups;
    for (let i=0; i<myGroups.length; i++) {
      if (myGroups[i].name.indexOf(this.query) > -1) {
        this.viewGroups.push(myGroups[i]);
      }
    }
  }

  getMessagesFromUser(username: string){
    //alert("Daj poruke od "+username);
    this.userService.setFriend(username);
  }

  getMessagesFromGroup(group: string){
    //alert("Daj poruke od "+group);
    this.userService.setCurrentGroup(group);
  }

}
