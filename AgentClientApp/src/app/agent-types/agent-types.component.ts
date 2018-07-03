import { Component, OnInit } from '@angular/core';
import { AgentsService } from '../services/agents.service';

@Component({
  selector: 'app-agent-types',
  templateUrl: './agent-types.component.html',
  styleUrls: ['./agent-types.component.css']
})
export class AgentTypesComponent implements OnInit {

  types: any[]

  constructor(
    private agentsService: AgentsService
  ) { 
    this.types = [];
  }

  ngOnInit() {
    this.agentsService.getTypes().subscribe(
      (data) => {
        this.types = data;
        for(let i=0; i<this.types.length; i++){
          this.types[i].agentForm = {
            opened: false,
            name: ''
          };
        }
      }
    )
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
