import { LOCALE_ID } from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';

import { OverlayContainer, FullscreenOverlayContainer } from '@angular/cdk/overlay';

import { MatPaginatorIntlBR } from 'sol/components/form/elements/grid/grid.component';

import { Quark } from 'sol/utils/Quark';
import { DateUtils }  from 'sol/utils/DateUtils';
import { DialogUtils } from 'sol/utils/DialogUtils';
import { SearchUtils } from 'sol/utils/SearchUtils';
import { FormUtils } from 'sol/utils/FormUtils';
import { MomentDateAdapter, MOMENT_DATE_FORMATS } from 'sol/utils/DateAdapter';

import { MatPaginatorIntl, MatSnackBar, MAT_DATE_FORMATS, DateAdapter, MatDateFormats } from '@angular/material';


export const PROVIDERS: any[] = [
  Quark,
  DateUtils,
  {provide: APP_BASE_HREF, useValue : '/' },
  {provide: LOCALE_ID, useValue: 'pt-BR' },
  {provide: MAT_DATE_FORMATS, useValue: MOMENT_DATE_FORMATS},
  {provide: DateAdapter, useClass: MomentDateAdapter},
  { provide: MatPaginatorIntl, useClass: MatPaginatorIntlBR},
  {provide: OverlayContainer, useClass: FullscreenOverlayContainer},

  // Material 2
  MatSnackBar,

  // utils
  DialogUtils,
  SearchUtils
];
