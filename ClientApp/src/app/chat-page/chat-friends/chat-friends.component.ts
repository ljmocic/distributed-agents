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
  }

  addFriend(user: User){
    this.userService.addFriend(user);
    alert("Dodajem osobu "+user.username+"  kao prijatelja!");
  }

  removeFriend(user: User){
    this.userService.removeFriend(user);
    alert("Ne zelim osobu "+user.username+" kao prijatelja!");
  }

  checkIfMyFriend(user: User) : boolean {
    return this.userService.checkIfMyFriend(user);
  }

}
