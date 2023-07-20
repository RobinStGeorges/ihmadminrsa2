import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EquipementFormService } from './equipement-form.service';
import { EquipementService } from '../service/equipement.service';
import { IEquipement } from '../equipement.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

import { EquipementUpdateComponent } from './equipement-update.component';

describe('Equipement Management Update Component', () => {
  let comp: EquipementUpdateComponent;
  let fixture: ComponentFixture<EquipementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let equipementFormService: EquipementFormService;
  let equipementService: EquipementService;
  let localisationService: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EquipementUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EquipementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EquipementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    equipementFormService = TestBed.inject(EquipementFormService);
    equipementService = TestBed.inject(EquipementService);
    localisationService = TestBed.inject(LocalisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Localisation query and add missing value', () => {
      const equipement: IEquipement = { id: 'CBA' };
      const localisation: ILocalisation = { id: '4b36d907-0799-4e7e-acdc-26bfe76e3ff9' };
      equipement.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: '22507406-9c1e-461c-9907-ae1c7070edc7' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ equipement });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const equipement: IEquipement = { id: 'CBA' };
      const localisation: ILocalisation = { id: '36cb0f61-c6ad-4ed8-98cc-1de3c0078b41' };
      equipement.localisation = localisation;

      activatedRoute.data = of({ equipement });
      comp.ngOnInit();

      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.equipement).toEqual(equipement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEquipement>>();
      const equipement = { id: 'ABC' };
      jest.spyOn(equipementFormService, 'getEquipement').mockReturnValue(equipement);
      jest.spyOn(equipementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ equipement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: equipement }));
      saveSubject.complete();

      // THEN
      expect(equipementFormService.getEquipement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(equipementService.update).toHaveBeenCalledWith(expect.objectContaining(equipement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEquipement>>();
      const equipement = { id: 'ABC' };
      jest.spyOn(equipementFormService, 'getEquipement').mockReturnValue({ id: null });
      jest.spyOn(equipementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ equipement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: equipement }));
      saveSubject.complete();

      // THEN
      expect(equipementFormService.getEquipement).toHaveBeenCalled();
      expect(equipementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEquipement>>();
      const equipement = { id: 'ABC' };
      jest.spyOn(equipementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ equipement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(equipementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocalisation', () => {
      it('Should forward to localisationService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(localisationService, 'compareLocalisation');
        comp.compareLocalisation(entity, entity2);
        expect(localisationService.compareLocalisation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
