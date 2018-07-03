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
      }
    )
  }

}
