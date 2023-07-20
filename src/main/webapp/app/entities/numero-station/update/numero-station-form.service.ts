import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INumeroStation, NewNumeroStation } from '../numero-station.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INumeroStation for edit and NewNumeroStationFormGroupInput for create.
 */
type NumeroStationFormGroupInput = INumeroStation | PartialWithRequiredKeyOf<NewNumeroStation>;

type NumeroStationFormDefaults = Pick<NewNumeroStation, 'id'>;

type NumeroStationFormGroupContent = {
  id: FormControl<INumeroStation['id'] | NewNumeroStation['id']>;
  numeroStation: FormControl<INumeroStation['numeroStation']>;
  equipement: FormControl<INumeroStation['equipement']>;
};

export type NumeroStationFormGroup = FormGroup<NumeroStationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NumeroStationFormService {
  createNumeroStationFormGroup(numeroStation: NumeroStationFormGroupInput = { id: null }): NumeroStationFormGroup {
    const numeroStationRawValue = {
      ...this.getFormDefaults(),
      ...numeroStation,
    };
    return new FormGroup<NumeroStationFormGroupContent>({
      id: new FormControl(
        { value: numeroStationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numeroStation: new FormControl(numeroStationRawValue.numeroStation, {
        validators: [Validators.pattern('[Z]{1}[0-9]{3}[-]{1}[0-9]{7}')],
      }),
      equipement: new FormControl(numeroStationRawValue.equipement),
    });
  }

  getNumeroStation(form: NumeroStationFormGroup): INumeroStation | NewNumeroStation {
    return form.getRawValue() as INumeroStation | NewNumeroStation;
  }

  resetForm(form: NumeroStationFormGroup, numeroStation: NumeroStationFormGroupInput): void {
    const numeroStationRawValue = { ...this.getFormDefaults(), ...numeroStation };
    form.reset(
      {
        ...numeroStationRawValue,
        id: { value: numeroStationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NumeroStationFormDefaults {
    return {
      id: null,
    };
  }
}
