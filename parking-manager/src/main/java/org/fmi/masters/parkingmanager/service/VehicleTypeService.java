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

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.service.filter.VehicleTypeFilter;


@Path("/vehicle-type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface VehicleTypeService {

    @GET
    @Path("/")
    Collection<VehicleType> findAll(@NotNull @BeanParam VehicleTypeFilter filter);

    @GET
    @Path("/{name}")
    VehicleType findByName(@NotNull @NotEmpty @PathParam("name") String name);

    @GET
    @Path("/{id}")
    VehicleType findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull VehicleType vehicleType);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);

}
