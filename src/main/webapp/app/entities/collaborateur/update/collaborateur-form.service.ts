import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICollaborateur, NewCollaborateur } from '../collaborateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICollaborateur for edit and NewCollaborateurFormGroupInput for create.
 */
type CollaborateurFormGroupInput = ICollaborateur | PartialWithRequiredKeyOf<NewCollaborateur>;

type CollaborateurFormDefaults = Pick<NewCollaborateur, 'id' | 'projets' | 'equipements' | 'cps' | 'dps'>;

type CollaborateurFormGroupContent = {
  id: FormControl<ICollaborateur['id'] | NewCollaborateur['id']>;
  identifiant: FormControl<ICollaborateur['identifiant']>;
  matricule: FormControl<ICollaborateur['matricule']>;
  societe: FormControl<ICollaborateur['societe']>;
  email: FormControl<ICollaborateur['email']>;
  tel: FormControl<ICollaborateur['tel']>;
  profil: FormControl<ICollaborateur['profil']>;
  dateEntree: FormControl<ICollaborateur['dateEntree']>;
  dateSortie: FormControl<ICollaborateur['dateSortie']>;
  statut: FormControl<ICollaborateur['statut']>;
  commentaire: FormControl<ICollaborateur['commentaire']>;
  projets: FormControl<ICollaborateur['projets']>;
  equipements: FormControl<ICollaborateur['equipements']>;
  localisation: FormControl<ICollaborateur['localisation']>;
  cps: FormControl<ICollaborateur['cps']>;
  dps: FormControl<ICollaborateur['dps']>;
};

export type CollaborateurFormGroup = FormGroup<CollaborateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CollaborateurFormService {
  createCollaborateurFormGroup(collaborateur: CollaborateurFormGroupInput = { id: null }): CollaborateurFormGroup {
    const collaborateurRawValue = {
      ...this.getFormDefaults(),
      ...collaborateur,
    };
    return new FormGroup<CollaborateurFormGroupContent>({
      id: new FormControl(
        { value: collaborateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      identifiant: new FormControl(collaborateurRawValue.identifiant, {
        validators: [Validators.required],
      }),
      matricule: new FormControl(collaborateurRawValue.matricule),
      societe: new FormControl(collaborateurRawValue.societe),
      email: new FormControl(collaborateurRawValue.email),
      tel: new FormControl(collaborateurRawValue.tel, {
        validators: [Validators.pattern('^((\\+)33|0|0033)[1-9](\\d{2}){4}$')],
      }),
      profil: new FormControl(collaborateurRawValue.profil),
      dateEntree: new FormControl(collaborateurRawValue.dateEntree),
      dateSortie: new FormControl(collaborateurRawValue.dateSortie),
      statut: new FormControl(collaborateurRawValue.statut),
      commentaire: new FormControl(collaborateurRawValue.commentaire),
      projets: new FormControl(collaborateurRawValue.projets ?? []),
      equipements: new FormControl(collaborateurRawValue.equipements ?? []),
      localisation: new FormControl(collaborateurRawValue.localisation),
      cps: new FormControl(collaborateurRawValue.cps ?? []),
      dps: new FormControl(collaborateurRawValue.dps ?? []),
    });
  }

  getCollaborateur(form: CollaborateurFormGroup): ICollaborateur | NewCollaborateur {
    return form.getRawValue() as ICollaborateur | NewCollaborateur;
  }

  resetForm(form: CollaborateurFormGroup, collaborateur: CollaborateurFormGroupInput): void {
    const collaborateurRawValue = { ...this.getFormDefaults(), ...collaborateur };
    form.reset(
      {
        ...collaborateurRawValue,
        id: { value: collaborateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CollaborateurFormDefaults {
    return {
      id: null,
      projets: [],
      equipements: [],
      cps: [],
      dps: [],
    };
  }
}
