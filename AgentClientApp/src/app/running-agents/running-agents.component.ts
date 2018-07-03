import { Component, OnInit } from '@angular/core';
import { AgentsService } from '../services/agents.service';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  runningAgents: any[]

  constructor(
    agentsService: AgentsService
  ) { 
  }

  ngOnInit() {
  }

}
