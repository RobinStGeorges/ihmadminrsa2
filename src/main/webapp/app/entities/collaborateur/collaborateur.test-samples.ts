import dayjs from 'dayjs/esm';

import { StatutCollaborateur } from 'app/entities/enumerations/statut-collaborateur.model';

import { ICollaborateur, NewCollaborateur } from './collaborateur.model';

export const sampleWithRequiredData: ICollaborateur = {
  id: 'b6c36038-72e7-4985-a125-77070b87f66d',
  identifiant: 'Books olive North',
};

export const sampleWithPartialData: ICollaborateur = {
  id: 'b203aef1-82fa-4fa4-8150-cefa022c7527',
  identifiant: 'Auto',
  matricule: 'Unbranded',
  societe: 'wiggly Male Concrete',
  email: 'Elwin.Murphy-Padberg@gmail.com',
  profil: 'Northeast',
  dateSortie: dayjs('2023-07-20'),
};

export const sampleWithFullData: ICollaborateur = {
  id: 'b7d341ac-d180-4d77-9e2e-8e4047f7eeda',
  identifiant: 'knowingly',
  matricule: 'Fantastic',
  societe: 'New',
  email: 'Lew.Ratke@yahoo.com',
  tel: '+33372703678',
  profil: 'Personal',
  dateEntree: dayjs('2023-07-19'),
  dateSortie: dayjs('2023-07-19'),
  statut: 'Sortie',
  commentaire: 'connecting though',
};

export const sampleWithNewData: NewCollaborateur = {
  identifiant: 'Lead Group Hatchback',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
