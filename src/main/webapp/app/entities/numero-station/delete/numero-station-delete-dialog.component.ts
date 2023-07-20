import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { INumeroStation } from '../numero-station.model';
import { NumeroStationService } from '../service/numero-station.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './numero-station-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class NumeroStationDeleteDialogComponent {
  numeroStation?: INumeroStation;

  constructor(protected numeroStationService: NumeroStationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.numeroStationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
