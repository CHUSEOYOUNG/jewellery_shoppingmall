package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.dao.ProductDao;
import com.sist.web.dao.ProductFileDao;
import com.sist.web.model.Product;
import com.sist.web.model.ProductFile;


@Service("productService")
public class ProductService 
{
	private static Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductDao productDao;
	//상품 리스트
	public List<Product> productList(Product product)
	{
		List<Product> list = null;
		
		try
		{
			list = productDao.productList(product);
		}
		catch(Exception e)
		{
			logger.error("[ProductService]productList Exception", e);
		}
		
		return list;
	} 
	

	
    public int updateProductImage(Product product) {
        return productDao.updateProductImage(product);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int productInsert(Product product) throws Exception 
    {
    	int count = 0;
    	count = productDao.productInsert(product);
    	
        return count;
        
    }
    
    public Product productSelect(long productId) {
        return productDao.productSelect(productId);
    }

        @Autowired
        private ProductFileDao productFileDao;

        
        public int insertProductFile(ProductFile file) throws Exception {
            return productFileDao.insertProductFile(file);
        }

        
        public int insertProductFileList(List<ProductFile> fileList) throws Exception {
            int count = 0;
            
            // 반복문으로 하나씩 insertProductFile 호출
            for(ProductFile file : fileList) {
                count += productFileDao.insertProductFile(file);
            }
            
            return count;
        }


        public List<ProductFile> selectProductFileList(String productId) {
            return productFileDao.selectProductFileList(productId);
        }

        public int deleteProductFileByProductId(String productId) throws Exception {
            return productFileDao.deleteProductFileByProductId(productId);
        }
        
        public int updateProduct(Product product) throws Exception {
            return productDao.updateProduct(product);
        }


}
