import { Injectable } from "@angular/core";
import { AgentsService } from "./agents.service";

 
@Injectable()
export class WebSocketService{   
    websocketRA: any;
    websocketTY: any;
    runningAgents: any[];
    types: any[];

    constructor(
      private agentsService: AgentsService
    ) {

      this.runningAgents = [];
      this.types = [];
      this.agentsService.getRunningAgents().subscribe(
        (data) => {
          this.runningAgents = data;
        }
      )

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

      this.websocketRA = new WebSocket('ws://127.0.0.1:8080/AgentAppWeb/websocket/running-agents');
      this.websocketRA.onopen = (event) => {
        console.log('open');
      };
      this.websocketRA.onclose = (event) => {
        console.log('close');
      };
      this.websocketRA.onmessage = (event) => {
        console.log('message');
        this.runningAgents = JSON.parse(event.data);
      };
      this.websocketRA.onerror = (event) => {
        console.log('error');
      };

      this.websocketTY = new WebSocket('ws://127.0.0.1:8080/AgentAppWeb/websocket/agent-types');
      this.websocketTY.onopen = (event) => {
        console.log('open');
      };
      this.websocketTY.onclose = (event) => {
        console.log('close');
      };
      this.websocketTY.onmessage = (event) => {
        console.log('message');
        this.types = JSON.parse(event.data);
          for(let i=0; i<this.types.length; i++){
            this.types[i].agentForm = {
              opened: false,
              name: ''
            };
          }
      };
      this.websocketTY.onerror = (event) => {
        console.log('error');
      };
    }
}