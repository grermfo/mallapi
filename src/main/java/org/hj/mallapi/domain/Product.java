package org.hj.mallapi.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_product")
@Getter
@Builder
@ToString(exclude = "imageList")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long pno;

private String pName;

private int price;

private String pDesc;

private boolean delFlag;

@ElementCollection
@Builder.Default
private List<ProductImage> imageList = new ArrayList<>();

public void changeDesc(String desc) {
    this.pDesc = desc;
}

public void changeName(String name) {
    this.pName = name;
}

public void addImage(ProductImage image) {
    image.setOrd(this.imageList.size());
    imageList.add(image);
}
public void addImageString(String fileName){
    ProductImage pImage = ProductImage.builder()
                        .fileName(fileName)
                        .build();
    addImage(pImage);
}

public void clearList() {
    this.imageList.clear();
}

public void changeDelFlag(boolean delFlag) {
    this.delFlag =  delFlag;
}


public void changePricd(int price) {
    this.price =  price;
}



}
