package start.ddd.chapter_3.section_6;

import start.ddd.chapter_3.section_5.ProductId;

public class RegisterProductService {

    public ProductId registerNewProduct(NewProductRequest req){
        Store store = storeRepository.findById(req.getStoreId());
        checkNull(store);
        if(accoutn.isBlocked()){
            throw new RuntimeException("상품 등록이 막힌 상점");
        }

        ProductId id = productRepisotory.nextId();
        //상품 생성 로직..
    }
}
