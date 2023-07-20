import { ITeletravail, NewTeletravail } from './teletravail.model';

export const sampleWithRequiredData: ITeletravail = {
  id: 'a58dfc8c-fb16-4749-aae8-a1f437b0d688',
};

export const sampleWithPartialData: ITeletravail = {
  id: '9cde7293-6866-4220-937e-0537e3308e86',
  ipDGFIPFixe: '77.16.740.50',
  ipTeletravail: '61.4.73.490',
};

export const sampleWithFullData: ITeletravail = {
  id: '86b4991b-dc75-4e49-adef-fbfc33d2b2bd',
  ipDGFIPFixe: '417.376.33.11',
  ipTeletravail: '3.16.9.69',
  ipIPSEC: '157.205.88.543',
  isActif: true,
};

export const sampleWithNewData: NewTeletravail = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
