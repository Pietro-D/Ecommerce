import { Routes } from '@angular/router';
import { ProductsListComponent } from './components/products-list/products-list.component';
import { AppComponent } from './components/app-component/app.component';

export const routes: Routes = [
    { path: 'products/:category', component: ProductsListComponent },
    { path: 'products/search/:query', component: ProductsListComponent }
];
