import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProjetDetailComponent } from './projet-detail.component';

describe('Projet Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjetDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProjetDetailComponent,
              resolve: { projet: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ProjetDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load projet on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProjetDetailComponent);

      // THEN
      expect(instance.projet).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
