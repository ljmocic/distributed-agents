import { Component } from '@angular/core';
import { AgentsService } from './services/agents.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';


  public types: any[];
  public runningAgents: any[];

  constructor(
    private agentsService: AgentsService
  ){
    this.types = [];
    this.runningAgents = [];
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

    this.agentsService.getRunningAgents().subscribe(
      (data) => {
        this.runningAgents = data;
      }
    )
  }
}
