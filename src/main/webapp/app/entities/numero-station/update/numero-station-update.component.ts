import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NumeroStationFormService, NumeroStationFormGroup } from './numero-station-form.service';
import { INumeroStation } from '../numero-station.model';
import { NumeroStationService } from '../service/numero-station.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';

@Component({
  standalone: true,
  selector: 'jhi-numero-station-update',
  templateUrl: './numero-station-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NumeroStationUpdateComponent implements OnInit {
  isSaving = false;
  numeroStation: INumeroStation | null = null;

  equipementsCollection: IEquipement[] = [];

  editForm: NumeroStationFormGroup = this.numeroStationFormService.createNumeroStationFormGroup();

  constructor(
    protected numeroStationService: NumeroStationService,
    protected numeroStationFormService: NumeroStationFormService,
    protected equipementService: EquipementService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEquipement = (o1: IEquipement | null, o2: IEquipement | null): boolean => this.equipementService.compareEquipement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ numeroStation }) => {
      this.numeroStation = numeroStation;
      if (numeroStation) {
        this.updateForm(numeroStation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const numeroStation = this.numeroStationFormService.getNumeroStation(this.editForm);
    if (numeroStation.id !== null) {
      this.subscribeToSaveResponse(this.numeroStationService.update(numeroStation));
    } else {
      this.subscribeToSaveResponse(this.numeroStationService.create(numeroStation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INumeroStation>>): void {
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

  protected updateForm(numeroStation: INumeroStation): void {
    this.numeroStation = numeroStation;
    this.numeroStationFormService.resetForm(this.editForm, numeroStation);

    this.equipementsCollection = this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(
      this.equipementsCollection,
      numeroStation.equipement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.equipementService
      .query({ filter: 'numerostation-is-null' })
      .pipe(map((res: HttpResponse<IEquipement[]>) => res.body ?? []))
      .pipe(
        map((equipements: IEquipement[]) =>
          this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(equipements, this.numeroStation?.equipement)
        )
      )
      .subscribe((equipements: IEquipement[]) => (this.equipementsCollection = equipements));
  }
}
