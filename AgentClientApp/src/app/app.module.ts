import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AgentTypesComponent } from './agent-types/agent-types.component';
import { RunningAgentsComponent } from './running-agents/running-agents.component';
import { LogComponent } from './log/log.component';
import { MessageComponent } from './message/message.component';


@NgModule({
  declarations: [
    AppComponent,
    AgentTypesComponent,
    RunningAgentsComponent,
    LogComponent,
    MessageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
