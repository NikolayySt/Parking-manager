package org.fmi.masters.parkingmanager.service;

import java.util.List;

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

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.service.filter.DriverFilter;

@Path("/driver")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DriverService {

    @GET
    @Path("/")
    List<Driver> findAll(@NotNull @BeanParam DriverFilter filter);

    @GET
    @Path("/{id}")
    Driver findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull Driver driver);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);

}
