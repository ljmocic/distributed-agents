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
}
