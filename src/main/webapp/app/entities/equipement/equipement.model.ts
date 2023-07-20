import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { ICollaborateur } from 'app/entities/collaborateur/collaborateur.model';
import { EquipementType } from 'app/entities/enumerations/equipement-type.model';

export interface IEquipement {
  id: string;
  adressePhysique?: string | null;
  equipementType?: keyof typeof EquipementType | null;
  ip?: string | null;
  biosVersion?: string | null;
  commentaire?: string | null;
  asset?: string | null;
  numeroSerie?: string | null;
  modele?: string | null;
  generation?: string | null;
  marque?: string | null;
  fonctionnel?: boolean | null;
  cleAntiVolAdmin?: string | null;
  cleAntiVolCollaborateur?: string | null;
  localisation?: Pick<ILocalisation, 'id' | 'ville' | 'site' | 'bureau'> | null;
  collaborateurs?: Pick<ICollaborateur, 'id'>[] | null;
}

export type NewEquipement = Omit<IEquipement, 'id'> & { id: null };
