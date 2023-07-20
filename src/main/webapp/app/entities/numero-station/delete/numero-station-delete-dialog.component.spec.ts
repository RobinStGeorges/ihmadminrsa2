jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NumeroStationService } from '../service/numero-station.service';

import { NumeroStationDeleteDialogComponent } from './numero-station-delete-dialog.component';

describe('NumeroStation Management Delete Component', () => {
  let comp: NumeroStationDeleteDialogComponent;
  let fixture: ComponentFixture<NumeroStationDeleteDialogComponent>;
  let service: NumeroStationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, NumeroStationDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(NumeroStationDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NumeroStationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NumeroStationService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
