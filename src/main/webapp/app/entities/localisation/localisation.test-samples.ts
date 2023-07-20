import { Ville } from 'app/entities/enumerations/ville.model';
import { Site } from 'app/entities/enumerations/site.model';

import { ILocalisation, NewLocalisation } from './localisation.model';

export const sampleWithRequiredData: ILocalisation = {
  id: 'a70d40df-c0c8-4ce2-a0f2-b4965f192ffe',
  ville: 'Noisiel',
};

export const sampleWithPartialData: ILocalisation = {
  id: '247d496b-68af-49a9-b601-c39f4b5badcb',
  ville: 'Montreuil',
  batiment: 'application',
  etage: 30494,
  bureau: 'Kentucky Mouse',
  place: 'male Administrator lovable',
};

export const sampleWithFullData: ILocalisation = {
  id: 'ecb00031-a056-44f9-b26e-2295284d47ee',
  ville: 'Montreuil',
  site: 'Maille_Nord',
  batiment: 'Bismuth Extended Funk',
  etage: 7290,
  bureau: 'Stage Bigender',
  place: 'Southeast',
};

export const sampleWithNewData: NewLocalisation = {
  ville: 'Noisiel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
