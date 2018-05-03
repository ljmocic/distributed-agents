import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FrontPageComponent } from './front-page/front-page.component';
import { ChatPageComponent } from './chat-page/chat-page.component';
import { frontPageRoute } from './front-page/front-page.route';
import { chatPageRoute } from './chat-page/chat-page.route';

const routes : Routes = [
  chatPageRoute,
  frontPageRoute
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports:[
    RouterModule
  ]
})
export class AppRoutingModule { }
