import { Component, OnInit, Input } from '@angular/core';
import { AgentsService } from '../services/agents.service';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  @Input() runningAgents: any[];

  constructor(
    private agentsService: AgentsService
  ) { 
  }

  ngOnInit() {
    
  }

  stopAgent(agent: any){
    //{{{agent.name}} [{{agent.type.name}}] @ {{agent.host.alias}}
    const aid = "name="+agent.name+",type="+agent.type.name+",host="+agent.host.alias;
    this.agentsService.stopAgent(aid).subscribe(
      (data) => {
        alert("success");
        this.runningAgents.splice(this.runningAgents.indexOf(agent), 1);
      }
    )
  }

}
