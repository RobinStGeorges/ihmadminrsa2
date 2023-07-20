import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LocalisationDetailComponent } from './localisation-detail.component';

describe('Localisation Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LocalisationDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LocalisationDetailComponent,
              resolve: { localisation: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(LocalisationDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load localisation on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LocalisationDetailComponent);

      // THEN
      expect(instance.localisation).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
