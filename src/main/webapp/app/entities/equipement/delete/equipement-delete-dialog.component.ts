import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IEquipement } from '../equipement.model';
import { EquipementService } from '../service/equipement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './equipement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EquipementDeleteDialogComponent {
  equipement?: IEquipement;

  constructor(protected equipementService: EquipementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.equipementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
