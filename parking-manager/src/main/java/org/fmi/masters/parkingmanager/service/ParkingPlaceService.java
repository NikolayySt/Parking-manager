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

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;



@Path("/parking-place")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParkingPlaceService {

    @GET
    @Path("/")
    Collection<ParkingPlace> findAll(@NotNull @BeanParam ParkingPlaceFilter filter);

    @GET
    @Path("{name}/{parking}")
    ParkingPlace findByNameAndParking(@NotNull @NotEmpty @PathParam("name") String name, @NotNull @PathParam("parking") Parking parking);

    @GET
    @Path("/{id}")
    ParkingPlace findById(@NotNull @PathParam("id") Long id);

    @POST
    @Path("/")
    void save(@NotNull ParkingPlace bean);

    @DELETE
    @Path("/{id}")
    void deleteById(@NotNull @PathParam("id") Long id);

}
