import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITeletravail } from '../teletravail.model';
import { TeletravailService } from '../service/teletravail.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './teletravail-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TeletravailDeleteDialogComponent {
  teletravail?: ITeletravail;

  constructor(protected teletravailService: TeletravailService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.teletravailService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
