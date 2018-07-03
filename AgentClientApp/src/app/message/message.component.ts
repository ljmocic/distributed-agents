import { Component, OnInit, Input } from '@angular/core';
import { MessageService } from '../services/message.service';
import { Message } from './message';
import { AgentsService } from '../services/agents.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  performatives: string[];
  message: Message;
  @Input() runningAgents: any[];

  constructor(
    private messageService: MessageService,
    private agentService: AgentsService
  ) { 
    this.message = {
      performative: '',
      sender: null,
      receivers: [],
      content: '',
      conversationId: '',
      encoding: '',
      inReplyTo: '',
      language: '',
      ontology: '',
      protocol: '',
      replyBy: 0,
      replyTo: null,
      replyWith: ''
    }
  }

  ngOnInit() {
    this.performatives = [];
    this.messageService.getPerformatives().subscribe(
      (data) => {
        this.performatives = data;
      }
    )
  }

  send(){
    //this.message.performative = this.performatives.indexOf(this.message.performative);
    this.messageService.sendMessage(this.message).subscribe(
      (data) => {
        alert("success!");
      }
    )
  }

}
