package com.sist.web.dao;

import java.util.List;
import com.sist.web.model.Product;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public interface ProductDao {

	    public int productInsert(Product product);
	    public int updateProductImage(Product product);
	    public List<Product> productList(Product search);

	    public Product productSelect(long productId); 
	    public int updateProduct(Product product);


}