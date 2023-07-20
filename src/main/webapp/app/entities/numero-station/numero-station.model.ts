import { IEquipement } from 'app/entities/equipement/equipement.model';

export interface INumeroStation {
  id: string;
  numeroStation?: string | null;
  equipement?: Pick<IEquipement, 'id'> | null;
}

export type NewNumeroStation = Omit<INumeroStation, 'id'> & { id: null };
