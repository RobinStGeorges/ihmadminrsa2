import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../teletravail.test-samples';

import { TeletravailFormService } from './teletravail-form.service';

describe('Teletravail Form Service', () => {
  let service: TeletravailFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeletravailFormService);
  });

  describe('Service methods', () => {
    describe('createTeletravailFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTeletravailFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipDGFIPFixe: expect.any(Object),
            ipTeletravail: expect.any(Object),
            ipIPSEC: expect.any(Object),
            isActif: expect.any(Object),
            equipement: expect.any(Object),
          })
        );
      });

      it('passing ITeletravail should create a new form with FormGroup', () => {
        const formGroup = service.createTeletravailFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipDGFIPFixe: expect.any(Object),
            ipTeletravail: expect.any(Object),
            ipIPSEC: expect.any(Object),
            isActif: expect.any(Object),
            equipement: expect.any(Object),
          })
        );
      });
    });

    describe('getTeletravail', () => {
      it('should return NewTeletravail for default Teletravail initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTeletravailFormGroup(sampleWithNewData);

        const teletravail = service.getTeletravail(formGroup) as any;

        expect(teletravail).toMatchObject(sampleWithNewData);
      });

      it('should return NewTeletravail for empty Teletravail initial value', () => {
        const formGroup = service.createTeletravailFormGroup();

        const teletravail = service.getTeletravail(formGroup) as any;

        expect(teletravail).toMatchObject({});
      });

      it('should return ITeletravail', () => {
        const formGroup = service.createTeletravailFormGroup(sampleWithRequiredData);

        const teletravail = service.getTeletravail(formGroup) as any;

        expect(teletravail).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITeletravail should not enable id FormControl', () => {
        const formGroup = service.createTeletravailFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTeletravail should disable id FormControl', () => {
        const formGroup = service.createTeletravailFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
