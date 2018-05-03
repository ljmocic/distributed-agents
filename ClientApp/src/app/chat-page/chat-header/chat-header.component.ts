import { Component, OnInit } from '@angular/core';
import { Group } from '../../models/group';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { Router } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-chat-header',
  templateUrl: './chat-header.component.html',
  styleUrls: ['./chat-header.component.css']
})
export class ChatHeaderComponent implements OnInit {

  newGroupOpen: boolean;
  name: string;
  groupError: boolean;

  constructor(
    private userService: UserService,
    private router: Router,
    private groupService: GroupService
  ) {
  }

  ngOnInit() {
    this.name = "";
    this.newGroupOpen = false;
    this.groupError = false;
  }

  createGroup(){
    if(this.groupError){
      return;
    }
    if(this.name === "" || this.name === null){
      this.groupError = true;
      this.name = "Name your group";
      return;
    }

    this.groupService.createNewGroup(this.name, this.userService.getCurrentLoggedUser());
    this.newGroupOpen = false;
  }

  manageGroupForm(){
    if(this.newGroupOpen){
      this.newGroupOpen = false;
      this.name = "";
    }else{
      this.name = "";
      this.newGroupOpen = true;
      this.groupError = false;
    }
  }

  focusOn(){
    if(this.groupError){
      this.name = "";
    }
    this.groupError = false;
  }

  logout() {
    this.userService.logoutUser();
  }

}
