import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../numero-station.test-samples';

import { NumeroStationFormService } from './numero-station-form.service';

describe('NumeroStation Form Service', () => {
  let service: NumeroStationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NumeroStationFormService);
  });

  describe('Service methods', () => {
    describe('createNumeroStationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNumeroStationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroStation: expect.any(Object),
            equipement: expect.any(Object),
          })
        );
      });

      it('passing INumeroStation should create a new form with FormGroup', () => {
        const formGroup = service.createNumeroStationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroStation: expect.any(Object),
            equipement: expect.any(Object),
          })
        );
      });
    });

    describe('getNumeroStation', () => {
      it('should return NewNumeroStation for default NumeroStation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNumeroStationFormGroup(sampleWithNewData);

        const numeroStation = service.getNumeroStation(formGroup) as any;

        expect(numeroStation).toMatchObject(sampleWithNewData);
      });

      it('should return NewNumeroStation for empty NumeroStation initial value', () => {
        const formGroup = service.createNumeroStationFormGroup();

        const numeroStation = service.getNumeroStation(formGroup) as any;

        expect(numeroStation).toMatchObject({});
      });

      it('should return INumeroStation', () => {
        const formGroup = service.createNumeroStationFormGroup(sampleWithRequiredData);

        const numeroStation = service.getNumeroStation(formGroup) as any;

        expect(numeroStation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INumeroStation should not enable id FormControl', () => {
        const formGroup = service.createNumeroStationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNumeroStation should disable id FormControl', () => {
        const formGroup = service.createNumeroStationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
