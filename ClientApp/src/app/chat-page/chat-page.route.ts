import { Route } from '@angular/router';
import { ChatPageComponent } from './chat-page.component';
import { ChatFriendsComponent } from './chat-friends/chat-friends.component';
import { ChatMessagesComponent } from './chat-messages/chat-messages.component';

export const chatPageRoute: Route = {
    path: 'messages',
    component: ChatPageComponent,
    children: [
        {
            path: 'friends',
            component: ChatFriendsComponent
        },
        {
            path: '',
            component: ChatMessagesComponent
        }
    ]
};