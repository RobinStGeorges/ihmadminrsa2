import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'collaborateur',
        data: { pageTitle: 'ihmadminRsa2App.collaborateur.home.title' },
        loadChildren: () => import('./collaborateur/collaborateur.routes'),
      },
      {
        path: 'equipement',
        data: { pageTitle: 'ihmadminRsa2App.equipement.home.title' },
        loadChildren: () => import('./equipement/equipement.routes'),
      },
      {
        path: 'localisation',
        data: { pageTitle: 'ihmadminRsa2App.localisation.home.title' },
        loadChildren: () => import('./localisation/localisation.routes'),
      },
      {
        path: 'numero-station',
        data: { pageTitle: 'ihmadminRsa2App.numeroStation.home.title' },
        loadChildren: () => import('./numero-station/numero-station.routes'),
      },
      {
        path: 'projet',
        data: { pageTitle: 'ihmadminRsa2App.projet.home.title' },
        loadChildren: () => import('./projet/projet.routes'),
      },
      {
        path: 'teletravail',
        data: { pageTitle: 'ihmadminRsa2App.teletravail.home.title' },
        loadChildren: () => import('./teletravail/teletravail.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
