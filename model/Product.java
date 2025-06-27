package com.sist.web.model;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
public class Product  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3701802364269801970L;
	private long productId;
    private String productName;
    private String categoryId;
    private int productPrice;
    private String productImage;
    private String productDesc;
    private String regDate;
    private String productOnoff;
    private String categoryName; 
    private long productStock;
	private String searchType; 		//검색타입(1: 이름, 2: 제목, 3: 내용)
	private String searchValue;
    private long startRow;
    private long endRow;
    private transient MultipartFile uploadFile;

    
    public Product()
    {
    	productId = 0;
        productName = "";
        categoryId = "";
        productPrice = 0;
        productImage = "";
        productDesc = "";
        regDate  = "";
        productOnoff = "";
        categoryName = "";
        productStock = 0;
		searchType = "";
		searchValue = "";
        startRow = 0;
        endRow = 0;

        
    }
    
    public MultipartFile getUploadFile() {
        return uploadFile;
    }
    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }


	public long getProductStock() {
		return productStock;
	}


	public void setProductStock(long productStock) {
		this.productStock = productStock;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public String getSearchValue() {
		return searchValue;
	}


	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}



public long getProductId() {
    return productId;
}
public void setProductId(long productId) {
    this.productId = productId;
}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductOnoff() {
		return productOnoff;
	}
	public void setProductOnoff(String productOnoff) {
		this.productOnoff = productOnoff;
	}
	public String getCategoryName() {
	    return categoryName;
	}

	public void setCategoryName(String categoryName) {
	    this.categoryName = categoryName;
	}
	public long getStartRow() {
		return startRow;
	}
	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}
	public long getEndRow() {
		return endRow;
	}
	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}


}
