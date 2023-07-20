import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITeletravail, NewTeletravail } from '../teletravail.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeletravail for edit and NewTeletravailFormGroupInput for create.
 */
type TeletravailFormGroupInput = ITeletravail | PartialWithRequiredKeyOf<NewTeletravail>;

type TeletravailFormDefaults = Pick<NewTeletravail, 'id' | 'isActif'>;

type TeletravailFormGroupContent = {
  id: FormControl<ITeletravail['id'] | NewTeletravail['id']>;
  ipDGFIPFixe: FormControl<ITeletravail['ipDGFIPFixe']>;
  ipTeletravail: FormControl<ITeletravail['ipTeletravail']>;
  ipIPSEC: FormControl<ITeletravail['ipIPSEC']>;
  isActif: FormControl<ITeletravail['isActif']>;
  equipement: FormControl<ITeletravail['equipement']>;
};

export type TeletravailFormGroup = FormGroup<TeletravailFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeletravailFormService {
  createTeletravailFormGroup(teletravail: TeletravailFormGroupInput = { id: null }): TeletravailFormGroup {
    const teletravailRawValue = {
      ...this.getFormDefaults(),
      ...teletravail,
    };
    return new FormGroup<TeletravailFormGroupContent>({
      id: new FormControl(
        { value: teletravailRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ipDGFIPFixe: new FormControl(teletravailRawValue.ipDGFIPFixe, {
        validators: [Validators.pattern('[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}')],
      }),
      ipTeletravail: new FormControl(teletravailRawValue.ipTeletravail, {
        validators: [Validators.pattern('[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}')],
      }),
      ipIPSEC: new FormControl(teletravailRawValue.ipIPSEC, {
        validators: [Validators.pattern('[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}')],
      }),
      isActif: new FormControl(teletravailRawValue.isActif),
      equipement: new FormControl(teletravailRawValue.equipement),
    });
  }

  getTeletravail(form: TeletravailFormGroup): ITeletravail | NewTeletravail {
    return form.getRawValue() as ITeletravail | NewTeletravail;
  }

  resetForm(form: TeletravailFormGroup, teletravail: TeletravailFormGroupInput): void {
    const teletravailRawValue = { ...this.getFormDefaults(), ...teletravail };
    form.reset(
      {
        ...teletravailRawValue,
        id: { value: teletravailRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TeletravailFormDefaults {
    return {
      id: null,
      isActif: false,
    };
  }
}
