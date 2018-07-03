import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Message } from '../message/message';

@Injectable()
export class MessageService {

  constructor(
    private http: HttpClient
  ) { }

  getPerformatives(): Observable<any>{
    return this.http.get('http://127.0.0.1:8080/AgentAppWeb/rest/messages');
  }

  sendMessage(msg: Message){
    const headers = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.post('http://127.0.0.1:8080/AgentAppWeb/rest/messages', JSON.stringify(msg), headers});
  }
}
