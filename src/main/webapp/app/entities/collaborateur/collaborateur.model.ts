import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { StatutCollaborateur } from 'app/entities/enumerations/statut-collaborateur.model';

export interface ICollaborateur {
  id: string;
  identifiant?: string | null;
  matricule?: string | null;
  societe?: string | null;
  email?: string | null;
  tel?: string | null;
  profil?: string | null;
  dateEntree?: dayjs.Dayjs | null;
  dateSortie?: dayjs.Dayjs | null;
  statut?: keyof typeof StatutCollaborateur | null;
  commentaire?: string | null;
  projets?: Pick<IProjet, 'id' | 'nom'>[] | null;
  equipements?: Pick<IEquipement, 'id' | 'asset' | 'equipementType'>[] | null;
  localisation?: Pick<ILocalisation, 'id' | 'ville' | 'site' | 'bureau'> | null;
  cps?: Pick<IProjet, 'id' | 'nom'>[] | null;
  dps?: Pick<IProjet, 'id' | 'nom'>[] | null;
}

export type NewCollaborateur = Omit<ICollaborateur, 'id'> & { id: null };
