package org.fmi.masters.parkingmanager.service;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceTypeFilter;

@Path("/place-type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ParkingPlaceTypeService {

    @GET
    @Path("/")
    Collection<ParkingPlaceType> findAll(@NotNull @BeanParam ParkingPlaceTypeFilter filter);

    @GET
    @Path("/{name}")
    ParkingPlaceType findByName(@NotNull @NotEmpty @PathParam("name") String name);

    @GET
    @Path("/{id}")
    ParkingPlaceType findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull ParkingPlaceType bean);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);
}
