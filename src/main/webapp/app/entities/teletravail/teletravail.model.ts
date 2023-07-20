import { IEquipement } from 'app/entities/equipement/equipement.model';

export interface ITeletravail {
  id: string;
  ipDGFIPFixe?: string | null;
  ipTeletravail?: string | null;
  ipIPSEC?: string | null;
  isActif?: boolean | null;
  equipement?: Pick<IEquipement, 'id' | 'asset'> | null;
}

export type NewTeletravail = Omit<ITeletravail, 'id'> & { id: null };
