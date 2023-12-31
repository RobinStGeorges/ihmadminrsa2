import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LocalisationFormService, LocalisationFormGroup } from './localisation-form.service';
import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';
import { Ville } from 'app/entities/enumerations/ville.model';
import { Site } from 'app/entities/enumerations/site.model';

@Component({
  standalone: true,
  selector: 'jhi-localisation-update',
  templateUrl: './localisation-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LocalisationUpdateComponent implements OnInit {
  isSaving = false;
  localisation: ILocalisation | null = null;
  villeValues = Object.keys(Ville);
  siteValues = Object.keys(Site);

  editForm: LocalisationFormGroup = this.localisationFormService.createLocalisationFormGroup();

  constructor(
    protected localisationService: LocalisationService,
    protected localisationFormService: LocalisationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localisation }) => {
      this.localisation = localisation;
      if (localisation) {
        this.updateForm(localisation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localisation = this.localisationFormService.getLocalisation(this.editForm);
    if (localisation.id !== null) {
      this.subscribeToSaveResponse(this.localisationService.update(localisation));
    } else {
      this.subscribeToSaveResponse(this.localisationService.create(localisation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>): void {
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

  protected updateForm(localisation: ILocalisation): void {
    this.localisation = localisation;
    this.localisationFormService.resetForm(this.editForm, localisation);
  }
}
