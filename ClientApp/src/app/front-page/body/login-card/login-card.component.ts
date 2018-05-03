import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
import { GroupService } from '../../../services/group.service';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {
  
  user: User;

  usernameAlert: string;
  passwordAlert: string;

  constructor(
    private userService: UserService,
    private router: Router,
    private groupService: GroupService
  ) { 
  }

  ngOnInit() {
    this.user = new User("", "", "", "");
    this.usernameAlert=null;
    this.passwordAlert=null;
  }

  loginUser(){
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

    this.userService.loginUser(this.user);
    this.groupService.myGroups = this.groupService.getGroupsOf(this.userService.me.username);
    this.router.navigate(['/messages']);
  }
}
