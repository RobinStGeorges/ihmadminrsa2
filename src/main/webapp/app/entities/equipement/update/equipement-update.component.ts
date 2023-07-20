import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EquipementFormService, EquipementFormGroup } from './equipement-form.service';
import { IEquipement } from '../equipement.model';
import { EquipementService } from '../service/equipement.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { EquipementType } from 'app/entities/enumerations/equipement-type.model';

@Component({
  standalone: true,
  selector: 'jhi-equipement-update',
  templateUrl: './equipement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EquipementUpdateComponent implements OnInit {
  isSaving = false;
  equipement: IEquipement | null = null;
  equipementTypeValues = Object.keys(EquipementType);

  localisationsSharedCollection: ILocalisation[] = [];

  editForm: EquipementFormGroup = this.equipementFormService.createEquipementFormGroup();

  constructor(
    protected equipementService: EquipementService,
    protected equipementFormService: EquipementFormService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipement }) => {
      this.equipement = equipement;
      if (equipement) {
        this.updateForm(equipement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipement = this.equipementFormService.getEquipement(this.editForm);
    if (equipement.id !== null) {
      this.subscribeToSaveResponse(this.equipementService.update(equipement));
    } else {
      this.subscribeToSaveResponse(this.equipementService.create(equipement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipement>>): void {
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

  protected updateForm(equipement: IEquipement): void {
    this.equipement = equipement;
    this.equipementFormService.resetForm(this.editForm, equipement);

    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      equipement.localisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.equipement?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));
  }
}
