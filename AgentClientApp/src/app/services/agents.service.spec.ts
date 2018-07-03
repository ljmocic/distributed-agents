import { TestBed, inject } from '@angular/core/testing';

import { AgentsService } from './agents.service';

describe('AgentsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AgentsService]
    });
  });

  it('should be created', inject([AgentsService], (service: AgentsService) => {
    expect(service).toBeTruthy();
  }));
});
