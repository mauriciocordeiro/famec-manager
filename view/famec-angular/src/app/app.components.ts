import { AppComponent } from 'app/app.component';

/* DEPENDENCIAS */
import { ConfirmDialog, AlertDialog, ModalDialog } from 'sol/components/dialog/dialog.component';
import { LoadingComponent } from 'sol/components/loading/loading.component';
import { FormComponent } from 'sol/components/form/form.component';

import { ButtonComponent, ComboboxComponent, GridComponent, LookupComponent,
         ListComponent, TextComponent, TextareaComponent, LineComponent,
         DropdownComponent, CheckboxComponent, DatepickerComponent, RadioComponent } from 'sol/components/form';

import { ImageGalleryComponent } from 'sol/components/image-gallery/image-gallery.component';
import { ReportViewerComponent } from 'sol/components/report-viewer/report-viewer.component';


export const COMPONENTS : object = {
    "AppComponent": AppComponent,
    "ConfirmDialog": ConfirmDialog,
    "AlertDialog": AlertDialog,
    "ModalDialog": ModalDialog,
    "LoadingComponent": LoadingComponent,
    "FormComponent": FormComponent,
    "TextComponent": TextComponent,
    "TextareaComponent": TextareaComponent,
    "LineComponent": LineComponent,
    "DropdownComponent": DropdownComponent,
    "ComboboxComponent": ComboboxComponent,
    "CheckboxComponent": CheckboxComponent,
    "RadioComponent": RadioComponent,
    "DatepickerComponent" : DatepickerComponent,
    "ButtonComponent" : ButtonComponent,
    "GridComponent" : GridComponent,
    "ListComponent" : ListComponent,
    "LookupComponent" : LookupComponent,
    "ImageGalleryComponent" : ImageGalleryComponent,
    "ReportViewerComponent" : ReportViewerComponent
}

export const COMPONENTS_ARRAY : any = [];
for (let key in COMPONENTS){
    COMPONENTS_ARRAY.push(COMPONENTS[key]);
}
