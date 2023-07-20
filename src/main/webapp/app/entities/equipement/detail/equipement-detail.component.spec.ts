import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EquipementDetailComponent } from './equipement-detail.component';

describe('Equipement Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EquipementDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EquipementDetailComponent,
              resolve: { equipement: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(EquipementDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load equipement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EquipementDetailComponent);

      // THEN
      expect(instance.equipement).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
