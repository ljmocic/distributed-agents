import { Component, OnInit, Input } from '@angular/core';
import { AgentsService } from '../services/agents.service';

@Component({
  selector: 'app-agent-types',
  templateUrl: './agent-types.component.html',
  styleUrls: ['./agent-types.component.css']
})
export class AgentTypesComponent implements OnInit {

  @Input() types: any[];

  constructor(
    private agentsService: AgentsService
  ) {
  }

  ngOnInit() {
   
  }

  openForm(type: any){
    type.agentForm.opened = !type.agentForm.opened;
  }

  startAgent(type: any){
    this.agentsService.startAgent(type).subscribe(
      (data) => {
        alert("success");
        type.agentForm.opened = false;
        type.agentForm.name = '';
      }
    )
  }

}
