package org.shop.manager.hexagonal.adapters.primary.rest;


import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.api.FindProduct;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.application.query.FindProductQueryMother;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.vocabulary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateProduct createProduct;
    @MockBean
    private UpdateProduct updateProduct;
    @MockBean
    private FindProduct findProduct;
    @MockBean
    private DeleteProduct deleteProduct;


    @Test
    void createProductReturnsSuccess() throws Exception {

        // BUILD (given)
        var body = validPostProductPayload();
        var expectedProductAfterPost = expectedProductAfterPost();
        whenCreateProductDo(presenter -> presenter.success(expectedProductAfterPost));

        // OPERATE (when)
        var response = postProduct(body);

        // CHECK (then)
        response.andExpect(status().isCreated());
    }

    @Test
    void createProductReturnsFailureWhenInvalidSerialNumberIsProvided() throws Exception {

        var body = postProductPayloadWithInvalidSerialNumber();
        whenCreateProductDo(presenter -> {throw new InvalidSerialNumberException("The serial number provided is not valid");});

        var response = postProduct(body);

        response.andExpect(status().isBadRequest());
    }

    @Test
    void createProductReturnsFailureWhenPayloadIsNotWellFormed() throws Exception {

        var body = postProductPayloadNotWellFormed();

        var response = postProduct(body);

        response.andExpect(status().isBadRequest());
    }

    @Test
    void updateExistingProductReturnsSuccess() throws Exception {

        var body = validPutProductPayload();
        var expectedProductAfterPut = expectedProductAfterPut();
        whenUpdateProductDo(presenter -> presenter.successAfterUpdate(expectedProductAfterPut));

        var response = putProduct(Identifier.nextVal().asString(), body);

        response.andExpect(status().isOk());
    }

    @Test
    void updateAndCreateNonExistentProductReturnsSuccess() throws Exception {

        var body = validPutProductPayload();
        var expectedProductAfterPut = expectedProductAfterPut();
        whenUpdateProductDo(presenter -> presenter.successAfterCreation(expectedProductAfterPut));

        var response = putProduct(Identifier.nextVal().asString(), body);

        response.andExpect(status().isCreated());
    }

    @Test
    void updateProductReturnsFailureWhenInvalidBarCodeIsProvided() throws Exception {

        var body = putProductPayloadWithInvalidBarCode();
        whenCreateProductDo(presenter -> {throw new InvalidBarCodeException("The barcode provided is not valid");});

        var response = putProduct(Identifier.nextVal().asString(), body);

        response.andExpect(status().isBadRequest());
    }

    @Test
    void fetchAllProductsReturnsSuccess() throws Exception {
        var aListOfProductsQueryData = FindProductQueryMother.aListOfProductsQueryData();
        when(findProduct.findAll()).thenReturn(aListOfProductsQueryData);

        var response = fetchAllProducts();

        response.andExpect(status().isOk());
    }

    @Test
    void fetchAllProductsReturnsNotFound() throws Exception {

        var response = fetchAllProducts();

        response.andExpect(status().isNotFound());
    }

    @Test
    void fetchProductBySerialNumberReturnsSuccess() throws Exception {
        var aProductQueryData = FindProductQueryMother.aProductQueryData();
        var anExistingProductSerialNumber = aProductQueryData.serialNumber().serialNumber();

        when(findProduct.findBySerialNumber(anExistingProductSerialNumber))
                .thenReturn(Optional.of(aProductQueryData));

        var response = fetchProductBySerialNumber(anExistingProductSerialNumber);

        response.andExpect(status().isOk());
    }

    @Test
    void fetchProductBySerialNumberReturnsNotFound() throws Exception {

        when(findProduct.findBySerialNumber(any())).thenReturn(Optional.empty());

        var response = fetchProductBySerialNumber(any());

        response.andExpect(status().isNotFound());
    }

    @Test
    void deleteExistentProductReturnsNoContentWithSpecificMessage() throws Exception {

        var productToDelete = aProductToDelete();
        var productToDeleteSerialNumber = productToDelete.toSnapshot().serialNumber();

        whenDeleteProductDo(presenter -> presenter.success(productToDelete));

        var response = deleteProduct(productToDeleteSerialNumber.serialNumber());

        response.andExpect(status().isOk());
        response.andExpect(content().string("Deleted"));
    }

    @Test
    void deleteProductWithInvalidSerialNumberReturnsBadRequest() throws Exception{

        whenDeleteProductDo(DeleteProduct.Presenter::notFound);

        var response = deleteProduct("Invalid_Identifier");

        response.andExpect(status().isBadRequest());
    }

    @Test
    void deleteNonExistentProductReturnsNoContentWithSpecificMessage() throws Exception {

        whenDeleteProductDo(DeleteProduct.Presenter::notFound);

        var response = deleteProduct("dd5b64e5-11d5-439c-9940-191868ebe862");

        response.andExpect(status().isNoContent());
    }

    private String validPostProductPayload() {
        return "{\n" +
                "    \"serial_number\": \"17ddbee2-3081-4191-a9bd-1494fdaddeca\",\n" +
                "    \"bar_code\": \"12345678901234567890123456789012\",\n" +
                "    \"name\": \"sneakers\",\n" +
                "    \"description\": \"Fancy sneakers shoes for women and men\",\n" +
                "    \"price\": \"32.50\",\n" +
                "    \"status\": \"IN_STOCK\"\n" +
                "}";
    }

    private String postProductPayloadWithInvalidSerialNumber() {
        return "{\n" +
                "    \"serial_number\": \"17ddbee\",\n" +
                "    \"bar_code\": \"12345678901234567890123456789012\",\n" +
                "    \"name\": \"sneakers\",\n" +
                "    \"description\": \"Fancy sneakers shoes for women and men\",\n" +
                "    \"price\": \"32.50\",\n" +
                "    \"status\": \"IN_STOCK\"\n" +
                "}";
    }

    private String postProductPayloadNotWellFormed() {
        return "{\n" +
                "    description\": \"Fancy sneakers shoes for women and men\",\n" +
                "    \"price\": \"32.50\",\n" +
                "    \"status\": \"IN_STOCK\"\n" +
                "}";
    }

    private String validPutProductPayload() {
        return validPostProductPayload();
    }

    private String putProductPayloadWithInvalidBarCode() {
        return "{\n" +
                "    \"serial_number\": \"17ddbee2-3081-4191-a9bd-1494fdaddeca\",\n" +
                "    \"bar_code\": \"12345678901234\",\n" +
                "    \"name\": \"sneakers\",\n" +
                "    \"description\": \"Fancy sneakers shoes for women and men\",\n" +
                "    \"price\": \"32.50\",\n" +
                "    \"status\": \"IN_STOCK\"\n" +
                "}";
    }

    private Product expectedProductAfterPost() {
        return aProduct();
    }

    private Product expectedProductAfterPut() {
        return aProduct();
    }

    private Product aProductToDelete() {
        return aProduct();
    }

    private Product aProduct() {
        return Product.builder().id(Identifier.nextVal())
                .serialNumber(new SerialNumber("17ddbee2-3081-4191-a9bd-1494fdaddeca"))
                .barCode(new BarCode("12345678901234567890123456789012"))
                .name(new ProductName("sneakers"))
                .description("Fancy sneakers shoes for women and men")
                .price(new Price(32.50))
                .status(Status.valueOf("IN_STOCK")).build();
    }

    private void whenCreateProductDo(Consumer<CreateProduct.Presenter> consumer) {
        doAnswer(invocationOnMock -> {
            var presenter = invocationOnMock.getArgument(1, CreateProduct.Presenter.class);
            consumer.accept(presenter);
            return null;
        }).when(createProduct).execute(any(), any());
    }

    private void whenUpdateProductDo(Consumer<UpdateProduct.Presenter> consumer) {
        doAnswer(invocationOnMock -> {
            var presenter = invocationOnMock.getArgument(1, UpdateProduct.Presenter.class);
            consumer.accept(presenter);
            return null;
        }).when(updateProduct).execute(any(), any());
    }

    private void whenDeleteProductDo(Consumer<DeleteProduct.Presenter> consumer) {
        doAnswer(invocationOnMock -> {
            var presenter = invocationOnMock.getArgument(1, DeleteProduct.Presenter.class);
            consumer.accept(presenter);
            return null;
        }).when(deleteProduct).execute(any(), any());
    }

    private ResultActions postProduct(String body) throws Exception {
        return mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body));
    }

    private ResultActions putProduct(String serialNumber, String body) throws Exception {
        return mockMvc.perform(put("/products/" + serialNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body));
    }

    private ResultActions fetchAllProducts() throws Exception {
        return mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions fetchProductBySerialNumber(String serialNumber) throws Exception {
        return mockMvc.perform(get("/products/" + serialNumber)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions deleteProduct(String serialNumber) throws Exception {
        return  mockMvc.perform(delete("/products/" + serialNumber)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
