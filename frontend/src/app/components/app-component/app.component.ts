import { Component, OnDestroy, OnInit } from '@angular/core';
import { CategoryService } from '../../services/CategoryService';
import { CategoryDTO } from '../../dto/CategoryDTO';
import { SERVER_PATH } from '../../constants';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent implements OnInit{
  title = 'frontend';

  showCategories: boolean = false;

  categories!: CategoryDTO[];

  imageUrl!: string;

  constructor(private catService: CategoryService, private router: Router, private route: ActivatedRoute){}
  ngOnInit(): void {

    this.router.events.subscribe((event) => {
      if (event.constructor.name === 'NavigationEnd') {
          this.showCategories = this.route.firstChild == null;
      }
    });
    
    this.fetchProducts()
      
  }

  fetchProducts(){
    this.imageUrl=SERVER_PATH+"/images/";
      this.catService.getCategories().subscribe({
        next: (categories) => {
          this.categories = categories.map(category => ({
            name: category.name,
            icon_url: category.icon_url,
            id: category.id
            
        }));
          
        },
        error: (error) => {
          console.error("Error fetching categories: ", error)
        }
      });
  }

  redirectToProductsList(category: string){
    this.router.navigate(["/products",category])
  }

  search(query: string){
    this.router.navigate(["/products/search",query]).then(() => {
      window.location.reload();
    });
  }

  


}


