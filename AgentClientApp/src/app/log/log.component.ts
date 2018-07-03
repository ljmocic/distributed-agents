import { Component, OnInit } from '@angular/core';
import { LogService } from '../services/log.service';

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent implements OnInit {

  content: string;

  constructor(
    logService: LogService
  ) {
    this.content = "";
   }

  ngOnInit() {
  }

}
