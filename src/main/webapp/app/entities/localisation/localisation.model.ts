import { Ville } from 'app/entities/enumerations/ville.model';
import { Site } from 'app/entities/enumerations/site.model';

export interface ILocalisation {
  id: string;
  ville?: keyof typeof Ville | null;
  site?: keyof typeof Site | null;
  batiment?: string | null;
  etage?: number | null;
  bureau?: string | null;
  place?: string | null;
}

export type NewLocalisation = Omit<ILocalisation, 'id'> & { id: null };
