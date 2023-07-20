import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TeletravailDetailComponent } from './teletravail-detail.component';

describe('Teletravail Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeletravailDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TeletravailDetailComponent,
              resolve: { teletravail: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(TeletravailDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load teletravail on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TeletravailDetailComponent);

      // THEN
      expect(instance.teletravail).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
