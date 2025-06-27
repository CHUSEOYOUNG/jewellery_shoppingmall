package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.Product;
import com.sist.web.model.ProductFile;

public interface ProductFileDao
{
	public int insertProductFile(ProductFile file);
    
    public int insertProductFileList(List<ProductFile> fileList);
    
    public List<ProductFile> selectProductFileList(String productId);
    
    public int deleteProductFileByProductId(String productId);
    


}