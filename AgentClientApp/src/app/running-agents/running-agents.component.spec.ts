import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RunningAgentsComponent } from './running-agents.component';

describe('RunningAgentsComponent', () => {
  let component: RunningAgentsComponent;
  let fixture: ComponentFixture<RunningAgentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RunningAgentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RunningAgentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
