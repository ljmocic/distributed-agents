import { Component } from '@angular/core';
import { AgentsService } from './services/agents.service';
import { WebSocketService } from './services/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(
    private websocketService: WebSocketService
  ){
    
  }
}
