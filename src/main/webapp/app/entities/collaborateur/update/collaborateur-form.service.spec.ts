import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../collaborateur.test-samples';

import { CollaborateurFormService } from './collaborateur-form.service';

describe('Collaborateur Form Service', () => {
  let service: CollaborateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollaborateurFormService);
  });

  describe('Service methods', () => {
    describe('createCollaborateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCollaborateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            identifiant: expect.any(Object),
            matricule: expect.any(Object),
            societe: expect.any(Object),
            email: expect.any(Object),
            tel: expect.any(Object),
            profil: expect.any(Object),
            dateEntree: expect.any(Object),
            dateSortie: expect.any(Object),
            statut: expect.any(Object),
            commentaire: expect.any(Object),
            projets: expect.any(Object),
            equipements: expect.any(Object),
            localisation: expect.any(Object),
            cps: expect.any(Object),
            dps: expect.any(Object),
          })
        );
      });

      it('passing ICollaborateur should create a new form with FormGroup', () => {
        const formGroup = service.createCollaborateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            identifiant: expect.any(Object),
            matricule: expect.any(Object),
            societe: expect.any(Object),
            email: expect.any(Object),
            tel: expect.any(Object),
            profil: expect.any(Object),
            dateEntree: expect.any(Object),
            dateSortie: expect.any(Object),
            statut: expect.any(Object),
            commentaire: expect.any(Object),
            projets: expect.any(Object),
            equipements: expect.any(Object),
            localisation: expect.any(Object),
            cps: expect.any(Object),
            dps: expect.any(Object),
          })
        );
      });
    });

    describe('getCollaborateur', () => {
      it('should return NewCollaborateur for default Collaborateur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCollaborateurFormGroup(sampleWithNewData);

        const collaborateur = service.getCollaborateur(formGroup) as any;

        expect(collaborateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewCollaborateur for empty Collaborateur initial value', () => {
        const formGroup = service.createCollaborateurFormGroup();

        const collaborateur = service.getCollaborateur(formGroup) as any;

        expect(collaborateur).toMatchObject({});
      });

      it('should return ICollaborateur', () => {
        const formGroup = service.createCollaborateurFormGroup(sampleWithRequiredData);

        const collaborateur = service.getCollaborateur(formGroup) as any;

        expect(collaborateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICollaborateur should not enable id FormControl', () => {
        const formGroup = service.createCollaborateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCollaborateur should disable id FormControl', () => {
        const formGroup = service.createCollaborateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
