package ecommerce.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    
    private Integer id;

    private String name;

    private String icon_url;

    public CategoryDTO(Integer id,String name, String url){
        this.id=id;
        this.name=name;
        this.icon_url=url;
    }
    
}
