import { ICollaborateur } from 'app/entities/collaborateur/collaborateur.model';
import { SocieteCPDP } from 'app/entities/enumerations/societe-cpdp.model';

export interface IProjet {
  id: string;
  nom?: string | null;
  structure?: string | null;
  societeCPDP?: keyof typeof SocieteCPDP | null;
  cps?: Pick<ICollaborateur, 'id' | 'identifiant'>[] | null;
  dps?: Pick<ICollaborateur, 'id' | 'identifiant'>[] | null;
  collaborateurs?: Pick<ICollaborateur, 'id'>[] | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
