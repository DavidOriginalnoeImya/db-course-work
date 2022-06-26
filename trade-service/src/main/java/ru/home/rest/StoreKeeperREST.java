package ru.home.rest;

import org.jboss.logging.Logger;
import ru.home.controller.StoreKeeperController;
import ru.home.dto.DTOConverter;
import ru.home.dto.ProductNamesDTO;
import ru.home.dto.StorageProductAcceptDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/storekeeper")
public class StoreKeeperREST {
    private static final Logger LOGGER = Logger.getLogger(StoreKeeperREST.class.getSimpleName());
    @Inject
    StoreKeeperController storeKeeperController;

    @POST
    @Path("/product/accept")
    @Consumes(MediaType.APPLICATION_JSON)
    public void acceptProduct(StorageProductAcceptDTO storageProductAcceptDTO) {
        storeKeeperController.addProduct(DTOConverter.getProductFromProductAcceptDTO(storageProductAcceptDTO));
    }

    @POST
    @Path("/certificate/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createAvailableCertificate(ProductNamesDTO productNames) {
        return Response
                .ok(storeKeeperController.createAvailableCertificate(productNames))
                .header("Content-Disposition", "attachment; filename=\"Справка.docx\"")
                .build();
    }

    @GET
    @Path("/storage/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsFromStorage() {
        return Response
                .ok(storeKeeperController.getProductsFromStorage())
                .build();
    }

    @GET
    @Path("/storage/city/{product_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductCities(@PathParam("product_name") String productName) {
        return Response
                .ok(storeKeeperController.getProductCities(productName))
                .build();
    }

    @GET
    @Path("/storage/price")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductPrices(@QueryParam("name") String productName,
                                     @QueryParam("city") String productCity) {
        return Response
                .ok(storeKeeperController.getProductPrices(productName, productCity))
                .build();
    }
}
