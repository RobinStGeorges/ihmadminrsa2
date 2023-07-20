import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CollaborateurFormService, CollaborateurFormGroup } from './collaborateur-form.service';
import { ICollaborateur } from '../collaborateur.model';
import { CollaborateurService } from '../service/collaborateur.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { StatutCollaborateur } from 'app/entities/enumerations/statut-collaborateur.model';

@Component({
  standalone: true,
  selector: 'jhi-collaborateur-update',
  templateUrl: './collaborateur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CollaborateurUpdateComponent implements OnInit {
  isSaving = false;
  collaborateur: ICollaborateur | null = null;
  statutCollaborateurValues = Object.keys(StatutCollaborateur);

  projetsSharedCollection: IProjet[] = [];
  equipementsSharedCollection: IEquipement[] = [];
  localisationsSharedCollection: ILocalisation[] = [];

  editForm: CollaborateurFormGroup = this.collaborateurFormService.createCollaborateurFormGroup();

  constructor(
    protected collaborateurService: CollaborateurService,
    protected collaborateurFormService: CollaborateurFormService,
    protected projetService: ProjetService,
    protected equipementService: EquipementService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProjet = (o1: IProjet | null, o2: IProjet | null): boolean => this.projetService.compareProjet(o1, o2);

  compareEquipement = (o1: IEquipement | null, o2: IEquipement | null): boolean => this.equipementService.compareEquipement(o1, o2);

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateur }) => {
      this.collaborateur = collaborateur;
      if (collaborateur) {
        this.updateForm(collaborateur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collaborateur = this.collaborateurFormService.getCollaborateur(this.editForm);
    if (collaborateur.id !== null) {
      this.subscribeToSaveResponse(this.collaborateurService.update(collaborateur));
    } else {
      this.subscribeToSaveResponse(this.collaborateurService.create(collaborateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaborateur>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(collaborateur: ICollaborateur): void {
    this.collaborateur = collaborateur;
    this.collaborateurFormService.resetForm(this.editForm, collaborateur);

    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing<IProjet>(
      this.projetsSharedCollection,
      ...(collaborateur.projets ?? [])
    );
    this.equipementsSharedCollection = this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(
      this.equipementsSharedCollection,
      ...(collaborateur.equipements ?? [])
    );
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      collaborateur.localisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(
        map((projets: IProjet[]) =>
          this.projetService.addProjetToCollectionIfMissing<IProjet>(projets, ...(this.collaborateur?.projets ?? []))
        )
      )
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));

    this.equipementService
      .query()
      .pipe(map((res: HttpResponse<IEquipement[]>) => res.body ?? []))
      .pipe(
        map((equipements: IEquipement[]) =>
          this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(equipements, ...(this.collaborateur?.equipements ?? []))
        )
      )
      .subscribe((equipements: IEquipement[]) => (this.equipementsSharedCollection = equipements));

    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.collaborateur?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));
  }
}
