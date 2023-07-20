import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TeletravailFormService, TeletravailFormGroup } from './teletravail-form.service';
import { ITeletravail } from '../teletravail.model';
import { TeletravailService } from '../service/teletravail.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';

@Component({
  standalone: true,
  selector: 'jhi-teletravail-update',
  templateUrl: './teletravail-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TeletravailUpdateComponent implements OnInit {
  isSaving = false;
  teletravail: ITeletravail | null = null;

  equipementsCollection: IEquipement[] = [];

  editForm: TeletravailFormGroup = this.teletravailFormService.createTeletravailFormGroup();

  constructor(
    protected teletravailService: TeletravailService,
    protected teletravailFormService: TeletravailFormService,
    protected equipementService: EquipementService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEquipement = (o1: IEquipement | null, o2: IEquipement | null): boolean => this.equipementService.compareEquipement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teletravail }) => {
      this.teletravail = teletravail;
      if (teletravail) {
        this.updateForm(teletravail);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teletravail = this.teletravailFormService.getTeletravail(this.editForm);
    if (teletravail.id !== null) {
      this.subscribeToSaveResponse(this.teletravailService.update(teletravail));
    } else {
      this.subscribeToSaveResponse(this.teletravailService.create(teletravail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeletravail>>): void {
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

  protected updateForm(teletravail: ITeletravail): void {
    this.teletravail = teletravail;
    this.teletravailFormService.resetForm(this.editForm, teletravail);

    this.equipementsCollection = this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(
      this.equipementsCollection,
      teletravail.equipement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.equipementService
      .query({ filter: 'teletravail-is-null' })
      .pipe(map((res: HttpResponse<IEquipement[]>) => res.body ?? []))
      .pipe(
        map((equipements: IEquipement[]) =>
          this.equipementService.addEquipementToCollectionIfMissing<IEquipement>(equipements, this.teletravail?.equipement)
        )
      )
      .subscribe((equipements: IEquipement[]) => (this.equipementsCollection = equipements));
  }
}
