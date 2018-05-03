import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { GroupService } from '../../../services/group.service';
import { MessageService } from '../../../services/message.service';

@Component({
  selector: 'app-register-card',
  templateUrl: './register-card.component.html',
  styleUrls: ['./register-card.component.css']
})
export class RegisterCardComponent implements OnInit {

  user: User;

  usernameAlert: string;
  passwordAlert: string;

  constructor(
    private router: Router,
    private userService: UserService,
    private groupService: GroupService
  ) { }

  ngOnInit() {
    this.user = new User("", "", "", "");
    this.usernameAlert=null;
    this.passwordAlert=null;
  }

  createUser(){
    if (this.user.username === "" || this.user.username === null){
      this.usernameAlert="You need to enter a username!";
      return;
    }else{
      this.usernameAlert = null;
    }

    if (this.user.password === "" || this.user.password === null){
      this.passwordAlert="You need to enter a password";
      return;
    }else{
      this.passwordAlert=null;
    }

    this.userService.createUser(this.user);
    this.groupService.myGroups = [];
    this.router.navigate(['/messages']);
  }

}
