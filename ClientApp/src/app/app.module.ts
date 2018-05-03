import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { FrontPageComponent } from './front-page/front-page.component';
import { ChatPageComponent } from './chat-page/chat-page.component';
import { HeaderComponent } from './front-page/header/header.component';
import { BodyComponent } from './front-page/body/body.component';
import { ButtonsComponent } from './front-page/body/buttons/buttons.component';
import { LoginCardComponent } from './front-page/body/login-card/login-card.component';
import { RegisterCardComponent } from './front-page/body/register-card/register-card.component';

import { AppRoutingModule } from './/app-routing.module';
import { ChatHeaderComponent } from './chat-page/chat-header/chat-header.component';
import { ChatFriendsComponent } from './chat-page/chat-friends/chat-friends.component';
import { ChatBodyComponent } from './chat-page/chat-body/chat-body.component';
import { ChatMessageComponent } from './chat-page/chat-message/chat-message.component';
import { ChatOnlineComponent } from './chat-page/chat-online/chat-online.component';
import { ChatMessagesComponent } from './chat-page/chat-messages/chat-messages.component';
import { UserService } from './services/user.service';
import { GroupService } from './services/group.service';
import { MessageService } from './services/message.service';

@NgModule({
  declarations: [
    AppComponent,
    FrontPageComponent,
    ChatPageComponent,
    HeaderComponent,
    BodyComponent,
    ButtonsComponent,
    LoginCardComponent,
    RegisterCardComponent,
    ChatHeaderComponent,
    ChatFriendsComponent,
    ChatBodyComponent,
    ChatMessageComponent,
    ChatOnlineComponent,
    ChatMessagesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [UserService, GroupService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
