import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatCardModule,
  MatInputModule,
  MatMenuModule,
  MatIconModule,
  MatToolbarModule,
} from '@angular/material';

@NgModule({

  exports: [
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule
  ]
})

export class MaterialModule {}