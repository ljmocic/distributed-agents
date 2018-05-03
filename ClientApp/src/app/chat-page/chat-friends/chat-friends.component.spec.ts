import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatFriendsComponent } from './chat-friends.component';

describe('ChatFriendsComponent', () => {
  let component: ChatFriendsComponent;
  let fixture: ComponentFixture<ChatFriendsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatFriendsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatFriendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
