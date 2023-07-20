import { INumeroStation, NewNumeroStation } from './numero-station.model';

export const sampleWithRequiredData: INumeroStation = {
  id: '05576d5a-6b86-45e3-b7af-69065949013e',
};

export const sampleWithPartialData: INumeroStation = {
  id: 'd7d202b0-5c0b-497c-b071-808fe0b78c4c',
};

export const sampleWithFullData: INumeroStation = {
  id: 'd862abc9-cae6-407d-9a2c-86bf1a4d34bf',
  numeroStation: 'Z724-7632365',
};

export const sampleWithNewData: NewNumeroStation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
