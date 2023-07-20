import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeletravailFormService } from './teletravail-form.service';
import { TeletravailService } from '../service/teletravail.service';
import { ITeletravail } from '../teletravail.model';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';

import { TeletravailUpdateComponent } from './teletravail-update.component';

describe('Teletravail Management Update Component', () => {
  let comp: TeletravailUpdateComponent;
  let fixture: ComponentFixture<TeletravailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teletravailFormService: TeletravailFormService;
  let teletravailService: TeletravailService;
  let equipementService: EquipementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TeletravailUpdateComponent],
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
      .overrideTemplate(TeletravailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeletravailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teletravailFormService = TestBed.inject(TeletravailFormService);
    teletravailService = TestBed.inject(TeletravailService);
    equipementService = TestBed.inject(EquipementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call equipement query and add missing value', () => {
      const teletravail: ITeletravail = { id: 'CBA' };
      const equipement: IEquipement = { id: '2e2a3020-e6dc-4549-939e-c268a09ca2b3' };
      teletravail.equipement = equipement;

      const equipementCollection: IEquipement[] = [{ id: '7f976b7d-cb74-40d6-9edb-28788188b45e' }];
      jest.spyOn(equipementService, 'query').mockReturnValue(of(new HttpResponse({ body: equipementCollection })));
      const expectedCollection: IEquipement[] = [equipement, ...equipementCollection];
      jest.spyOn(equipementService, 'addEquipementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teletravail });
      comp.ngOnInit();

      expect(equipementService.query).toHaveBeenCalled();
      expect(equipementService.addEquipementToCollectionIfMissing).toHaveBeenCalledWith(equipementCollection, equipement);
      expect(comp.equipementsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teletravail: ITeletravail = { id: 'CBA' };
      const equipement: IEquipement = { id: '28c01e14-b3f1-4f8b-8456-4592ffe6a256' };
      teletravail.equipement = equipement;

      activatedRoute.data = of({ teletravail });
      comp.ngOnInit();

      expect(comp.equipementsCollection).toContain(equipement);
      expect(comp.teletravail).toEqual(teletravail);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeletravail>>();
      const teletravail = { id: 'ABC' };
      jest.spyOn(teletravailFormService, 'getTeletravail').mockReturnValue(teletravail);
      jest.spyOn(teletravailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teletravail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teletravail }));
      saveSubject.complete();

      // THEN
      expect(teletravailFormService.getTeletravail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teletravailService.update).toHaveBeenCalledWith(expect.objectContaining(teletravail));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeletravail>>();
      const teletravail = { id: 'ABC' };
      jest.spyOn(teletravailFormService, 'getTeletravail').mockReturnValue({ id: null });
      jest.spyOn(teletravailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teletravail: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teletravail }));
      saveSubject.complete();

      // THEN
      expect(teletravailFormService.getTeletravail).toHaveBeenCalled();
      expect(teletravailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeletravail>>();
      const teletravail = { id: 'ABC' };
      jest.spyOn(teletravailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teletravail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teletravailService.update).toHaveBeenCalled();
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
