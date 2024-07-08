import { TestBed } from '@angular/core/testing';

import { HttpManagerInterceptor } from './http-manager.interceptor';

describe('HttpManagerInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      HttpManagerInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: HttpManagerInterceptor = TestBed.inject(HttpManagerInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
