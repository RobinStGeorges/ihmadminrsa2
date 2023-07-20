import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NumeroStationFormService } from './numero-station-form.service';
import { NumeroStationService } from '../service/numero-station.service';
import { INumeroStation } from '../numero-station.model';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';

import { NumeroStationUpdateComponent } from './numero-station-update.component';

describe('NumeroStation Management Update Component', () => {
  let comp: NumeroStationUpdateComponent;
  let fixture: ComponentFixture<NumeroStationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let numeroStationFormService: NumeroStationFormService;
  let numeroStationService: NumeroStationService;
  let equipementService: EquipementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), NumeroStationUpdateComponent],
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
      .overrideTemplate(NumeroStationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NumeroStationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    numeroStationFormService = TestBed.inject(NumeroStationFormService);
    numeroStationService = TestBed.inject(NumeroStationService);
    equipementService = TestBed.inject(EquipementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call equipement query and add missing value', () => {
      const numeroStation: INumeroStation = { id: 'CBA' };
      const equipement: IEquipement = { id: '82b0d6f1-c36f-421d-a675-bf5fdde508c3' };
      numeroStation.equipement = equipement;

      const equipementCollection: IEquipement[] = [{ id: '2afe65a3-33fa-446c-ad3a-48bf8064e750' }];
      jest.spyOn(equipementService, 'query').mockReturnValue(of(new HttpResponse({ body: equipementCollection })));
      const expectedCollection: IEquipement[] = [equipement, ...equipementCollection];
      jest.spyOn(equipementService, 'addEquipementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ numeroStation });
      comp.ngOnInit();

      expect(equipementService.query).toHaveBeenCalled();
      expect(equipementService.addEquipementToCollectionIfMissing).toHaveBeenCalledWith(equipementCollection, equipement);
      expect(comp.equipementsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const numeroStation: INumeroStation = { id: 'CBA' };
      const equipement: IEquipement = { id: '99bcdd7e-9829-49a7-b3e9-39a324075c96' };
      numeroStation.equipement = equipement;

      activatedRoute.data = of({ numeroStation });
      comp.ngOnInit();

      expect(comp.equipementsCollection).toContain(equipement);
      expect(comp.numeroStation).toEqual(numeroStation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroStation>>();
      const numeroStation = { id: 'ABC' };
      jest.spyOn(numeroStationFormService, 'getNumeroStation').mockReturnValue(numeroStation);
      jest.spyOn(numeroStationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroStation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: numeroStation }));
      saveSubject.complete();

      // THEN
      expect(numeroStationFormService.getNumeroStation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(numeroStationService.update).toHaveBeenCalledWith(expect.objectContaining(numeroStation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroStation>>();
      const numeroStation = { id: 'ABC' };
      jest.spyOn(numeroStationFormService, 'getNumeroStation').mockReturnValue({ id: null });
      jest.spyOn(numeroStationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroStation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: numeroStation }));
      saveSubject.complete();

      // THEN
      expect(numeroStationFormService.getNumeroStation).toHaveBeenCalled();
      expect(numeroStationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroStation>>();
      const numeroStation = { id: 'ABC' };
      jest.spyOn(numeroStationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroStation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(numeroStationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEquipement', () => {
      it('Should forward to equipementService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(equipementService, 'compareEquipement');
        comp.compareEquipement(entity, entity2);
        expect(equipementService.compareEquipement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
