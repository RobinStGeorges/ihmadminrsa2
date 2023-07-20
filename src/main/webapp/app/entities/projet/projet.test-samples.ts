import { SocieteCPDP } from 'app/entities/enumerations/societe-cpdp.model';

import { IProjet, NewProjet } from './projet.model';

export const sampleWithRequiredData: IProjet = {
  id: 'c06f8ec1-4fd4-44cf-a4f4-a5e11761d73d',
  nom: 'Account Martin',
};

export const sampleWithPartialData: IProjet = {
  id: '35db0e05-2742-40ab-b9c8-a8072ff1a558',
  nom: 'Mouse',
  structure: 'Manager fuchsia Account',
  societeCPDP: 'DGFIP',
};

export const sampleWithFullData: IProjet = {
  id: '290a4c7e-217c-4961-853f-6f56516b1a42',
  nom: 'Toys',
  structure: 'Tesla watt maximize',
  societeCPDP: 'CGI',
};

export const sampleWithNewData: NewProjet = {
  nom: 'exercitationem',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
