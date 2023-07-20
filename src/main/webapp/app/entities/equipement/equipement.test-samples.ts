import { EquipementType } from 'app/entities/enumerations/equipement-type.model';

import { IEquipement, NewEquipement } from './equipement.model';

export const sampleWithRequiredData: IEquipement = {
  id: '2b931253-8191-4704-8972-4c064f80f5a0',
};

export const sampleWithPartialData: IEquipement = {
  id: 'af2c26af-2d79-4fda-8a65-8c95aceae9c3',
  equipementType: 'Ordinateur_Portable_DGFIP',
  asset: 'S31540679',
  numeroSerie: 'West Frozen Games',
  modele: 'Rap',
};

export const sampleWithFullData: IEquipement = {
  id: '1a8c8f92-cccd-4d6f-b896-3db688aacbe9',
  adressePhysique: 'M6|Tw|Hk:Ga-Tc:Z',
  equipementType: 'AdaptateurUsbEth',
  ip: 'solution Ford',
  biosVersion: 'interface',
  commentaire: 'generate Optimized Massachusetts',
  asset: 'S69189',
  numeroSerie: 'East copying',
  modele: 'West',
  generation: 'calculating Electric',
  marque: 'Phased',
  fonctionnel: true,
  cleAntiVolAdmin: 'Latin Forint Orchestrator',
  cleAntiVolCollaborateur: 'Executive delectus',
};

export const sampleWithNewData: NewEquipement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
