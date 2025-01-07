package de.berlin.htw.boundary;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import de.berlin.htw.boundary.dto.Basket;
import de.berlin.htw.boundary.dto.Item;
import de.berlin.htw.boundary.dto.Order;
import de.berlin.htw.control.BasketController;

import java.util.List;

@Path("/basket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BasketResource {

    @Context
    UriInfo uri;

    @Context
    SecurityContext context;

    @Inject
    BasketController basketController;

    @Inject
    Logger logger;

    @GET
    @Operation(summary = "Retrieve the basket with all items.")
    @APIResponse(responseCode = "200", description = "Retrieve all items in basket successfully")
    @APIResponse(responseCode = "401", description = "No or wrong User Id provided as header")
    public Response getBasket() {
        String userId = context.getUserPrincipal().getName();
        logger.info(userId + " is retrieving the basket.");
        Basket basket = basketController.getBasket(userId);
        return Response.ok(basket).build();
    }

    @DELETE
    @Operation(summary = "Remove all items from basket.")
    @APIResponse(responseCode = "204", description = "Basket cleared successfully")
    @APIResponse(responseCode = "401", description = "No or wrong User Id provided as header")
    public Response clearBasket() {
        String userId = context.getUserPrincipal().getName();
        logger.info(userId + " is clearing the basket.");
        basketController.clearBasket(userId);
        return Response.noContent().build();
    }

    @POST
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add an item to basket.")
    @APIResponse(responseCode = "201", description = "Item added successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Basket.class)) )
    @APIResponse(responseCode = "400", description = "Invalid request message")
    @APIResponse(responseCode = "401", description = "No or wrong User Id provided as header")
    @APIResponse(responseCode = "409", description = "Another product with this ID already exist in the basket")
    public Response addItem(
            @PathParam("productId") String productId,
            @Valid Item item) {

        String userId = context.getUserPrincipal().getName();
        logger.info(userId + " is adding item with ID " + productId + " to the basket.");

        basketController.addItemToBasket(userId, item);

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove an item from basket.")
    @APIResponse(responseCode = "200", description = "Item removed successfully",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Basket.class)) )
    @APIResponse(responseCode = "401", description = "No or wrong User Id provided as header")
    @APIResponse(responseCode = "404", description = "No product with this ID in the basket")
    public Response removeItem(
            @Parameter(description = "ID of the product", required = true) @PathParam("productId") final String productId) {
    	logger.info(context.getUserPrincipal().getName() 
    			+ " is calling " + uri.getAbsolutePath());
    	// return basket with remaining balance
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @PATCH
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Change the number of an item in the basket.")
    @APIResponse(responseCode = "200", description = "Number changed successfully",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Basket.class)) )
    @APIResponse(responseCode = "400", description = "Invalid request message")
    @APIResponse(responseCode = "401", description = "No or wrong User Id provided as header")
    @APIResponse(responseCode = "404", description = "No product with this ID in the basket")
    public Response changeCount(
            @Parameter(description = "ID of the product", required = true) @PathParam("productId") final String productId,
            @Parameter(description = "The number of that product in the basket", required = true) final Item item) {
    	logger.info(context.getUserPrincipal().getName() 
    			+ " is calling " + uri.getAbsolutePath());
    	// return basket with remaining balance
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

}