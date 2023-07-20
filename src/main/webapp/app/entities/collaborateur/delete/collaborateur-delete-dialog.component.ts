import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ICollaborateur } from '../collaborateur.model';
import { CollaborateurService } from '../service/collaborateur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './collaborateur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CollaborateurDeleteDialogComponent {
  collaborateur?: ICollaborateur;

  constructor(protected collaborateurService: CollaborateurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.collaborateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
