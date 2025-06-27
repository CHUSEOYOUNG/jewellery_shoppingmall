package com.sist.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sist.common.model.FileData;
import com.sist.common.util.StringUtil;
import com.sist.web.model.Product;
import com.sist.web.model.ProductFile;
import com.sist.web.model.Response;
import com.sist.web.service.ProductService;
import com.sist.web.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

@Controller("productController")
public class productController 
{
    private static Logger logger = LoggerFactory.getLogger(productController.class);

    @Autowired
    private ProductService productService;

    @Value("#{env['upload.save.dir']}")  // 기존에 있던 업로드 경로 사용
    private String UPLOAD_SAVE_DIR;
    
    @Value("#{env['upload.save.dir.detail']}")  // 기존에 있던 업로드 경로 사용
    private String UPLOAD_SAVE_DIR_DETAIL;

    /**
     * 등록 폼
     */
    @RequestMapping(value = "/product/productInsert", method = RequestMethod.GET)
    public String productInsertForm() {
        return "/product/productInsert";
    }

    /**
     * 등록 처리
     */
    @RequestMapping(value = "/product/insertProc", method = RequestMethod.POST)
    public String insertProc(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        // 일반정보
        String productName = request.getParameter("productName");
        String categoryId = request.getParameter("categoryId");
        int productPrice = Integer.parseInt(request.getParameter("productPrice"));
        long productStock = Long.parseLong(request.getParameter("productStock"));
        String productDesc = request.getParameter("productDesc");
        String productOnoff = request.getParameter("productOnoff");

        Product product = new Product();
        product.setProductName(productName);
        product.setCategoryId(categoryId);
        product.setProductPrice(productPrice);
        product.setProductStock(productStock);
        product.setProductDesc(productDesc);
        product.setProductOnoff(productOnoff);

        // 상품 insert → PK 생성
        productService.productInsert(product);

        // 대표 이미지 저장
        String myFileName = product.getProductId() + "";
        FileData fileData = HttpUtil.getFile(request, "uploadFile", UPLOAD_SAVE_DIR, myFileName);
        if (fileData != null && fileData.getFileSize() > 0) {
            product.setProductImage(fileData.getFileName());
            productService.updateProductImage(product);
        }

        // 상세 이미지 저장
        List<FileData> fileDataList = HttpUtil.getFiles(request, "productDetailFiles", UPLOAD_SAVE_DIR_DETAIL);
        List<ProductFile> fileList = new ArrayList<>();
        for (FileData detailFile : fileDataList) {
            ProductFile file = new ProductFile();
            file.setProductId(String.valueOf(product.getProductId()));
            file.setProductFileType("DETAIL");
            file.setProductFileName(detailFile.getFileName());
            file.setProductFilePath(detailFile.getFilePath());
            fileList.add(file);
        }
        if (!fileList.isEmpty()) {
            productService.insertProductFileList(fileList);
        }

        return "redirect:/product/productAdminList";
    }


    /**
     * 관리자용 상품 리스트
     */
    @RequestMapping(value = "/product/productAdminList")
    public String productAdminList(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
    {
        String searchType = HttpUtil.get(request, "searchType", "");
        String searchValue = HttpUtil.get(request, "searchValue", "");
        long curPage = HttpUtil.get(request, "curPage", 1L);

        Product search = new Product();

        if (!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
            search.setSearchType(searchType);
            search.setSearchValue(searchValue);
        }

        search.setStartRow(1);
        search.setEndRow(10);

        List<Product> list = productService.productList(search);
        model.addAttribute("productList", list);

        return "/product/productAdminList";
    }



    /**
     * 사용자용 상품 리스트
     */
    @RequestMapping(value = "/product/list")
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
    {
        String searchType = HttpUtil.get(request, "searchType", "");
        String searchValue = HttpUtil.get(request, "searchValue", "");
        String categoryId = HttpUtil.get(request, "categoryId", "");  // 추가됨
        long curPage = HttpUtil.get(request, "curPage", 1L);

        Product search = new Product();

        if (!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
            search.setSearchType(searchType);
            search.setSearchValue(searchValue);
        }

        if (!StringUtil.isEmpty(categoryId)) {
            search.setCategoryId(categoryId);  // 카테고리 조건 추가
        }

        search.setStartRow(1);
        search.setEndRow(10);

        List<Product> list = productService.productList(search);
        model.addAttribute("productList", list);

        logger.debug("==========================");
        logger.debug("list size :" + list.size());
        logger.debug("==========================");

        return "/product/list";
    }
    
    //상품 상세페이지
    @RequestMapping(value = "/product/view", method = RequestMethod.GET)
    public String productView(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
    {
        long productId = HttpUtil.get(request, "productId", 0L);

        Product product = productService.productSelect(productId);

        if(product == null) {
            model.addAttribute("errorMessage", "상품을 찾을 수 없습니다.");
            return "/product/view";  // view.jsp 안에서 처리
        }

        model.addAttribute("product", product);
        
        
        // 상세 이미지 목록 추가
        List<ProductFile> productFileList = productService.selectProductFileList(String.valueOf(productId));
        model.addAttribute("product", product);
        model.addAttribute("productFileList", productFileList); // ⭐ 추가

        return "/product/view";
    }
   
    @RequestMapping(value = "/product/productUpdateProc", method = RequestMethod.POST)
    public String productUpdateProc(MultipartHttpServletRequest request) throws Exception 
    {
        String productId = request.getParameter("productId");
        Product product = productService.productSelect(Long.parseLong(productId));

        if (product == null) {
            return "redirect:/error.jsp";
        }

        // 수정된 값 세팅
        product.setProductName(request.getParameter("productName"));
        product.setCategoryId(request.getParameter("categoryId"));
        product.setProductPrice(Integer.parseInt(request.getParameter("productPrice")));
        product.setProductStock(Long.parseLong(request.getParameter("productStock")));
        product.setProductDesc(request.getParameter("productDesc"));
        product.setProductOnoff(request.getParameter("productOnoff"));

        // 대표이미지 변경
        FileData fileData = HttpUtil.getFile(request, "uploadFile", UPLOAD_SAVE_DIR, productId);
        if (fileData != null && fileData.getFileSize() > 0) {
            product.setProductImage(fileData.getFileName());
        }
        productService.updateProduct(product);

        // 상세 이미지 추가 등록
        List<FileData> fileDataList = HttpUtil.getFiles(request, "productDetailFiles", UPLOAD_SAVE_DIR_DETAIL);
        List<ProductFile> fileList = new ArrayList<>();
        for (FileData detailFile : fileDataList) {
            ProductFile file = new ProductFile();
            file.setProductId(productId);
            file.setProductFileType("DETAIL");
            file.setProductFileName(detailFile.getFileName());
            file.setProductFilePath(detailFile.getFilePath());
            fileList.add(file);
        }
        if (!fileList.isEmpty()) {
            productService.insertProductFileList(fileList);
        }

        return "redirect:/product/productAdminList";
    }
    @RequestMapping(value = "/product/update", method = RequestMethod.GET)
    public String productUpdateForm(HttpServletRequest request, ModelMap model) 
    {
        long productId = HttpUtil.get(request, "productId", 0L);

        Product product = productService.productSelect(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "상품을 찾을 수 없습니다.");
            return "/product/view";  // 혹은 에러 페이지로
        }

        // 기존 데이터 전달
        model.addAttribute("product", product);

        return "/product/productUpdate";  // jsp 파일명 (주의!)
    }


}
