import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-chat-friends',
  templateUrl: './chat-friends.component.html',
  styleUrls: ['./chat-friends.component.css']
})
export class ChatFriendsComponent implements OnInit {

  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
    this.userService.getMyFriends();
    this.userService.getAllUsers();
  }

  addFriend(user: User){
    this.userService.addFriend(user);
  }

  removeFriend(user: User){
    this.userService.removeFriend(user);
  }

  checkIfMyFriend(user: User) : boolean {
    return this.userService.checkIfMyFriend(user);
  }

}
