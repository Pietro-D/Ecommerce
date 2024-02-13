import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { routes } from "./app.routes";

@NgModule({
    declarations: [],
    imports: [
      CommonModule,
      RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })
    ],
    exports:[RouterModule]
  })
  export class AppRoutingModule { }