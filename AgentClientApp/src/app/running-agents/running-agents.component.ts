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
    private agentsService: AgentsService
  ) { 
  }

  ngOnInit() {
    this.agentsService.getRunningAgents().subscribe(
      (data) => {
        this.runningAgents = data;
      }
    )
  }

  stopAgent(agent: any){
    //{{{agent.name}} [{{agent.type.name}}] @ {{agent.host.alias}}
    const aid = "name="+agent.name+",type="+agent.type.name+",host="+agent.host.alias;
    this.agentsService.stopAgent(aid).subscribe(
      (data) => {
        alert("success");
      }
    )
  }

}
