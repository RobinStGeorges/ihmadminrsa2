import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NumeroStationDetailComponent } from './numero-station-detail.component';

describe('NumeroStation Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NumeroStationDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: NumeroStationDetailComponent,
              resolve: { numeroStation: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(NumeroStationDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load numeroStation on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', NumeroStationDetailComponent);

      // THEN
      expect(instance.numeroStation).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
