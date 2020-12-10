package org.fmi.masters.parkingmanager.modules.driver;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.modules.driver.ui.DriversView;
import org.fmi.masters.parkingmanager.modules.driver.ui.dialog.DriverDialog;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverFilterBean;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverFilterView;
import org.fmi.masters.parkingmanager.service.DriverService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.VehicleService;
import org.fmi.masters.parkingmanager.service.filter.DriverFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;
import org.fmi.masters.parkingmanager.service.filter.VehicleFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class DriverPresenter implements DriversView.Presenter {

    @Resource
    private DriverService driverService;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private ParkingPlaceService parkingPlaceService;

    private DriversView view;

    private DriverFilterView filterView;

    private DriverFilterBean currentFilter;

    @Override
    public void onSubmit(DriverFilterBean filterBean) {
        if (filterBean != null) {
            currentFilter = filterBean;
        }
        else {
            currentFilter = new DriverFilterBean();
        }

        Collection<Driver> result = driverService.findAll(constructFilter(currentFilter));

        if (CollectionUtils.isEmpty(result)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setDrivers(result);
    }

    @Override
    public void setView(DriversView view) {
        this.view = view;
    }

    @Override
    public void onAdd() {
        DriverDialog dialog = new DriverDialog(new Driver(), vehicleService.findAll(new VehicleFilter()));
        dialog.setOnSave(this::onSave);

        dialog.open();
    }

    @Override
    public void onEdit(Driver selected) {
        if (selected == null) {
            Notification.show("Please select a driver in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        DriverDialog dialog = new DriverDialog(selected, vehicleService.findAll(new VehicleFilter()));
        dialog.setOnSave(this::onSave);

        dialog.open();
    }

    @Override
    public void onDelete(Driver selected) {
        if (selected == null) {
            Notification.show("Please select a driver in order to delete.", 1000, Position.MIDDLE);
            return;
        }
        Long driverId = selected.getId();

        ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();
        parkingPlaceFilter.setDriverId(driverId);

        if (!CollectionUtils.isEmpty(parkingPlaceService.findAll(parkingPlaceFilter))) {
            Notification.show("Deletion of this driver is not allowed. It`s connected to parking place record.", 3000, Position.MIDDLE);
            return;
        }

        driverService.deleteById(driverId);
        onSubmit(currentFilter);
    }

    private void onSave(Driver driver) {
        driverService.save(driver);
        onSubmit(currentFilter);
    }

    @Override
    public void setView(DriverFilterView view) {
        this.filterView = view;
    }

    @Override
    public void loadFilters() {
        filterView.setVehicles(vehicleService.findAll(new VehicleFilter()));
    }

    private DriverFilter constructFilter(DriverFilterBean filterBean) {
        DriverFilter filter = new DriverFilter();
        
        if (StringUtils.isNotBlank(filterBean.getFirstName())) {
            filter.setFirstName(filterBean.getFirstName());
        }
        
        if (StringUtils.isNotBlank(filterBean.getLastName())) {
            filter.setLastName(filterBean.getLastName());
        }
        
        if (filterBean.getVehicle() != null) {
            filter.setVehicleId(filterBean.getVehicle().getId());
        }
        
        return filter;
    }

}
