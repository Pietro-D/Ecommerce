import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDTO } from '../../dto/ProductDTO';
import { ProductService } from '../../services/ProductService';
import { PageEvent } from '@angular/material/paginator';
import { SERVER_PATH } from '../../constants';


@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrl: './products-list.component.scss'
})
export class ProductsListComponent implements OnInit{

  category!: string;
  products!: ProductDTO[];
  pageNumber: number=0
  pageSize: number=2;
  totalProducts!: number
  imageUrl: string= SERVER_PATH+"/images/"
  searchQuery!: string

  constructor(private route: ActivatedRoute, private prodService: ProductService) {}

  ngOnInit() {
    
    this.route.paramMap.subscribe((paramMap) => {
      this.category = paramMap.get('category') as string;
      this.searchQuery=paramMap.get("query") as string;
    });
    console.log(this.searchQuery)

    if(this.searchQuery){
      this.totalResultsSearch()
      this.fetchProductsSearch();
    }
    else{
      this.totalProductsCategory();
      this.fetchProductsCategory();
    }
    
  }

  totalResultsSearch(){
    this.prodService.totalResultsSearch(this.searchQuery).subscribe({
      next: (total) => {
        this.totalProducts=total;
      },
      error: (error) => {
        console.error("Error fetching products: ", error)
      }
    })
  }

  totalProductsCategory(){
    this.prodService.totalProducts(this.category).subscribe({
      next: (total) => {
        this.totalProducts=total;
      },
      error: (error) => {
        console.error("Error fetching products: ", error)
      }
    })
  }
  
  fetchProductsSearch(){
    this.prodService.searchProducts(this.searchQuery,this.pageNumber,this.pageSize).subscribe({
      next: (products) => {
        this.products = products;
      },
      error: (error) => {
        console.error("Error fetching products: ", error)
      }
    });
  }

  fetchProductsCategory(){
    this.prodService.getProducts(this.category,this.pageNumber,this.pageSize).subscribe({
      next: (products) => {
        this.products = products;
      },
      error: (error) => {
        console.error("Error fetching products: ", error)
      }
    });
    
  }

  onLoadMore(event:PageEvent){
    this.pageNumber=event.pageIndex;
    if(this.searchQuery)
      this.fetchProductsSearch();
    else
      this.fetchProductsCategory();
  }

}
