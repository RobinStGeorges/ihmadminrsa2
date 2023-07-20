import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../equipement.test-samples';

import { EquipementFormService } from './equipement-form.service';

describe('Equipement Form Service', () => {
  let service: EquipementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipementFormService);
  });

  describe('Service methods', () => {
    describe('createEquipementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEquipementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            adressePhysique: expect.any(Object),
            equipementType: expect.any(Object),
            ip: expect.any(Object),
            biosVersion: expect.any(Object),
            commentaire: expect.any(Object),
            asset: expect.any(Object),
            numeroSerie: expect.any(Object),
            modele: expect.any(Object),
            generation: expect.any(Object),
            marque: expect.any(Object),
            fonctionnel: expect.any(Object),
            cleAntiVolAdmin: expect.any(Object),
            cleAntiVolCollaborateur: expect.any(Object),
            localisation: expect.any(Object),
            collaborateurs: expect.any(Object),
          })
        );
      });

      it('passing IEquipement should create a new form with FormGroup', () => {
        const formGroup = service.createEquipementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            adressePhysique: expect.any(Object),
            equipementType: expect.any(Object),
            ip: expect.any(Object),
            biosVersion: expect.any(Object),
            commentaire: expect.any(Object),
            asset: expect.any(Object),
            numeroSerie: expect.any(Object),
            modele: expect.any(Object),
            generation: expect.any(Object),
            marque: expect.any(Object),
            fonctionnel: expect.any(Object),
            cleAntiVolAdmin: expect.any(Object),
            cleAntiVolCollaborateur: expect.any(Object),
            localisation: expect.any(Object),
            collaborateurs: expect.any(Object),
          })
        );
      });
    });

    describe('getEquipement', () => {
      it('should return NewEquipement for default Equipement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEquipementFormGroup(sampleWithNewData);

        const equipement = service.getEquipement(formGroup) as any;

        expect(equipement).toMatchObject(sampleWithNewData);
      });

      it('should return NewEquipement for empty Equipement initial value', () => {
        const formGroup = service.createEquipementFormGroup();

        const equipement = service.getEquipement(formGroup) as any;

        expect(equipement).toMatchObject({});
      });

      it('should return IEquipement', () => {
        const formGroup = service.createEquipementFormGroup(sampleWithRequiredData);

        const equipement = service.getEquipement(formGroup) as any;

        expect(equipement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEquipement should not enable id FormControl', () => {
        const formGroup = service.createEquipementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEquipement should disable id FormControl', () => {
        const formGroup = service.createEquipementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
