import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  nums: number[]
  limit = 25; 

  constructor() { 
    this.nums = [];
    for(let i=0; i<this.limit; i++){
      this.nums.push(i+1);
    }
  }

  ngOnInit() {
  }

}
