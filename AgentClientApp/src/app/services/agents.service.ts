import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AgentsService {

  constructor(
    private http: HttpClient
  ) { }

  getTypes(): Observable<any>{
    return this.http.get('http://127.0.0.1:8080/AgentAppWeb/rest/agents/classes');
  }

  getRunningAgents(): Observable<any>{
    return this.http.get('http://127.0.0.1:8080/AgentAppWeb/rest/agents/running');
  }

  startAgent(type: any): Observable<any>{
    return this.http.put('http://127.0.0.1:8080/AgentAppWeb/rest/agents/running/'+type.name+'/'+type.agentForm.name, null);
  }

  stopAgent(aid: string): Observable<any>{
    return this.http.delete('http://127.0.0.1:8080/AgentAppWeb/rest/agents/running/'+aid);
  }
}
