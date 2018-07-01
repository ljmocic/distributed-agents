import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-agent-types',
  templateUrl: './agent-types.component.html',
  styleUrls: ['./agent-types.component.css']
})
export class AgentTypesComponent implements OnInit {

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
