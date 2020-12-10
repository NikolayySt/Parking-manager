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

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.service.filter.VehicleFilter;

@Path("/vehicle")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface VehicleService {

    @GET
    @Path("/")
    Collection<Vehicle> findAll(@NotNull @BeanParam VehicleFilter filter);

    @GET
    @Path("/{numberPlate}")
    Vehicle findByNumberPlate(@NotNull @NotEmpty @PathParam("numberPlate") String numberPlate);

    @GET
    @Path("/{id}")
    Vehicle findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull Vehicle vehicle);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);

}
