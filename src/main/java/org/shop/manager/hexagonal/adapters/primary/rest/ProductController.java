package org.shop.manager.hexagonal.adapters.primary.rest;

import org.shop.manager.hexagonal.adapters.primary.rest.presenter.CreateProductPresenter;
import org.shop.manager.hexagonal.adapters.primary.rest.presenter.DeleteProductPresenter;
import org.shop.manager.hexagonal.adapters.primary.rest.presenter.UpdateProductPresenter;
import org.shop.manager.hexagonal.adapters.primary.rest.resource.CreateProductRequestResource;
import org.shop.manager.hexagonal.adapters.primary.rest.resource.ProductResponseResource;
import org.shop.manager.hexagonal.adapters.primary.rest.resource.UpdateProductRequestResource;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.api.FindProduct;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.vocabulary.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;
    private final FindProduct findProduct;
    private final DeleteProduct deleteProduct;

    public ProductController(CreateProduct createProduct,
                             UpdateProduct updateProduct,
                             FindProduct findProduct,
                             DeleteProduct deleteProduct) {
        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
        this.findProduct = findProduct;
        this.deleteProduct = deleteProduct;
    }

    @PostMapping
    public ResponseEntity<ProductResponseResource> createProduct(@RequestBody CreateProductRequestResource product) {
        CreateProductPresenter presenter = new CreateProductPresenter();

        createProduct.execute(new CreateProduct.Command(new SerialNumber(product.serialNumber()),
                new BarCode(product.barCode()),
                new ProductName(product.name()),
                product.description() != null ? product.description() : null,
                new Price(product.price()),
                Status.valueOf(product.status())), presenter);

        return presenter.toResponseEntity();
    }

    @PutMapping("/{serial-number}")
    public ResponseEntity<ProductResponseResource> updateProduct(@PathVariable("serial-number") String serialNumber,
                                                                 @RequestBody UpdateProductRequestResource product) {

        UpdateProductPresenter presenter = new UpdateProductPresenter();

        updateProduct.execute(new UpdateProduct.Command(new SerialNumber(serialNumber),
                new BarCode(product.barCode()),
                new ProductName(product.name()),
                product.description() != null ? product.description() : null,
                new Price(product.price()),
                Status.valueOf(product.status())), presenter);

        return presenter.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseResource>> getAllProducts() {

        var products = findProduct.findAll();

        return products.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(products.stream().map(ProductResponseResource::fromQueryData).toList());
    }

    @GetMapping("/{serial-number}")
    public ResponseEntity<ProductResponseResource> getProductBySerialNumber(@PathVariable("serial-number") String serialNumber) {

        var product = findProduct.findBySerialNumber(serialNumber);

        return product.map(ProductResponseResource::fromQueryData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{serial-number}")
    public ResponseEntity<String> deleteProduct(@PathVariable("serial-number") String serialNumber) {

        DeleteProductPresenter presenter = new DeleteProductPresenter();

        deleteProduct.execute(new DeleteProduct.Command(new SerialNumber(serialNumber)), presenter);

        return presenter.toResponseEntity();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
