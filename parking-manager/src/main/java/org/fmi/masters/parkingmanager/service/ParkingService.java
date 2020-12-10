package org.fmi.masters.parkingmanager.service;

import java.util.List;

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

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.service.filter.ParkingFilter;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParkingService {

    @GET
    @Path("/")
    List<Parking> findAll(@NotNull @BeanParam ParkingFilter parkingFilter);

    @GET
    @Path("/{name}")
    Parking findByName(@NotNull @NotEmpty @PathParam("name") String name);

    @GET
    @Path("/{id}")
    Parking findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull Parking bean);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);

}
