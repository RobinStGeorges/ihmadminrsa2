import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CollaborateurDetailComponent } from './collaborateur-detail.component';

describe('Collaborateur Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollaborateurDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CollaborateurDetailComponent,
              resolve: { collaborateur: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(CollaborateurDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load collaborateur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CollaborateurDetailComponent);

      // THEN
      expect(instance.collaborateur).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
