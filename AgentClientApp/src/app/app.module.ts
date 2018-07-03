import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AgentTypesComponent } from './agent-types/agent-types.component';
import { RunningAgentsComponent } from './running-agents/running-agents.component';
import { LogComponent } from './log/log.component';
import { MessageComponent } from './message/message.component';
import { AgentsService } from './services/agents.service';
import { LogService } from './services/log.service';
import { MessageService } from './services/message.service';
import { HttpClientModule } from '@angular/common/http';


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
    FormsModule,
    HttpClientModule
  ],
  providers: [AgentsService, LogService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
