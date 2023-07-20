import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEquipement, NewEquipement } from '../equipement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEquipement for edit and NewEquipementFormGroupInput for create.
 */
type EquipementFormGroupInput = IEquipement | PartialWithRequiredKeyOf<NewEquipement>;

type EquipementFormDefaults = Pick<NewEquipement, 'id' | 'fonctionnel' | 'collaborateurs'>;

type EquipementFormGroupContent = {
  id: FormControl<IEquipement['id'] | NewEquipement['id']>;
  adressePhysique: FormControl<IEquipement['adressePhysique']>;
  equipementType: FormControl<IEquipement['equipementType']>;
  ip: FormControl<IEquipement['ip']>;
  biosVersion: FormControl<IEquipement['biosVersion']>;
  commentaire: FormControl<IEquipement['commentaire']>;
  asset: FormControl<IEquipement['asset']>;
  numeroSerie: FormControl<IEquipement['numeroSerie']>;
  modele: FormControl<IEquipement['modele']>;
  generation: FormControl<IEquipement['generation']>;
  marque: FormControl<IEquipement['marque']>;
  fonctionnel: FormControl<IEquipement['fonctionnel']>;
  cleAntiVolAdmin: FormControl<IEquipement['cleAntiVolAdmin']>;
  cleAntiVolCollaborateur: FormControl<IEquipement['cleAntiVolCollaborateur']>;
  localisation: FormControl<IEquipement['localisation']>;
  collaborateurs: FormControl<IEquipement['collaborateurs']>;
};

export type EquipementFormGroup = FormGroup<EquipementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EquipementFormService {
  createEquipementFormGroup(equipement: EquipementFormGroupInput = { id: null }): EquipementFormGroup {
    const equipementRawValue = {
      ...this.getFormDefaults(),
      ...equipement,
    };
    return new FormGroup<EquipementFormGroupContent>({
      id: new FormControl(
        { value: equipementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      adressePhysique: new FormControl(equipementRawValue.adressePhysique, {
        validators: [
          Validators.pattern(
            '[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{0,1}[A-Za-z-0-9]{0,1}[A-Za-z-0-9]{0,1}'
          ),
        ],
      }),
      equipementType: new FormControl(equipementRawValue.equipementType),
      ip: new FormControl(equipementRawValue.ip),
      biosVersion: new FormControl(equipementRawValue.biosVersion),
      commentaire: new FormControl(equipementRawValue.commentaire),
      asset: new FormControl(equipementRawValue.asset, {
        validators: [Validators.pattern('[S]{1}[0-9]{5,8}')],
      }),
      numeroSerie: new FormControl(equipementRawValue.numeroSerie),
      modele: new FormControl(equipementRawValue.modele),
      generation: new FormControl(equipementRawValue.generation),
      marque: new FormControl(equipementRawValue.marque),
      fonctionnel: new FormControl(equipementRawValue.fonctionnel),
      cleAntiVolAdmin: new FormControl(equipementRawValue.cleAntiVolAdmin),
      cleAntiVolCollaborateur: new FormControl(equipementRawValue.cleAntiVolCollaborateur),
      localisation: new FormControl(equipementRawValue.localisation),
      collaborateurs: new FormControl(equipementRawValue.collaborateurs ?? []),
    });
  }

  getEquipement(form: EquipementFormGroup): IEquipement | NewEquipement {
    return form.getRawValue() as IEquipement | NewEquipement;
  }

  resetForm(form: EquipementFormGroup, equipement: EquipementFormGroupInput): void {
    const equipementRawValue = { ...this.getFormDefaults(), ...equipement };
    form.reset(
      {
        ...equipementRawValue,
        id: { value: equipementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EquipementFormDefaults {
    return {
      id: null,
      fonctionnel: false,
      collaborateurs: [],
    };
  }
}
