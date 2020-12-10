package org.fmi.masters.parkingmanager.modules.vehicle;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.VehicleView;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.dialog.VehicleDialog;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleFilterView;
import org.fmi.masters.parkingmanager.service.DriverService;
import org.fmi.masters.parkingmanager.service.VehicleService;
import org.fmi.masters.parkingmanager.service.VehicleTypeService;
import org.fmi.masters.parkingmanager.service.filter.DriverFilter;
import org.fmi.masters.parkingmanager.service.filter.VehicleFilter;
import org.fmi.masters.parkingmanager.service.filter.VehicleTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class VehiclePresenter implements VehicleView.Presenter {

    @Resource
    private VehicleService vehicleService;

    @Resource
    private VehicleTypeService vehicleTypeService;

    @Resource
    private DriverService driverService;

    private VehicleView view;

    private VehicleFilterView filterView;

    private VehicleFilterBean currentFilter;

    @Override
    public void onSubmit(VehicleFilterBean filterBean) {
        if (filterBean != null) {
            currentFilter = filterBean;
        }
        else {
            currentFilter = new VehicleFilterBean();
        }

        Collection<Vehicle> results = vehicleService.findAll(constructFilter(filterBean));

        if (CollectionUtils.isEmpty(results)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setVehicles(results);
    }

    @Override
    public void setView(VehicleView view) {
        this.view = view;
    }

    @Override
    public void onAdd() {
        VehicleDialog dialog = new VehicleDialog(new Vehicle(), vehicleTypeService.findAll(new VehicleTypeFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNumberPlateValidator(this::isNumberPlateUnique);

        dialog.open();
    }

    @Override
    public void onEdit(Vehicle vehicle) {
        if (vehicle == null) {
            Notification.show("Please select a vehicle in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        VehicleDialog dialog = new VehicleDialog(vehicle, vehicleTypeService.findAll(new VehicleTypeFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNumberPlateValidator(this::isNumberPlateUnique);

        dialog.open();
    }

    @Override
    public void onDelete(Vehicle vehicle) {
        if (vehicle == null) {
            Notification.show("Please select a vehicle in order to delete.", 1000, Position.MIDDLE);
            return;
        }

        Long vehicleId = vehicle.getId();

        DriverFilter driverFilter = new DriverFilter();
        driverFilter.setVehicleId(vehicleId);

        if (!CollectionUtils.isEmpty(driverService.findAll(driverFilter))) {
            Notification.show("Deletion of this vehicle is not allowed. It`s connected to driver record.", 3000, Position.MIDDLE);
            return;
        }

        vehicleService.deleteById(vehicleId);
        onSubmit(currentFilter);
    }

    private void onSave(Vehicle vehicle) {
        vehicleService.save(vehicle);
        
        onSubmit(currentFilter);
    }

    private boolean isNumberPlateUnique(String numberPlate) {
        if (StringUtils.isBlank(numberPlate)) {
            return false;
        }

        return vehicleService.findByNumberPlate(numberPlate.trim()) == null;
    }

    @Override
    public void setView(VehicleFilterView view) {
        this.filterView = view;
    }

    @Override
    public void loadFilters() {
        filterView.setVehicleTypes(vehicleTypeService.findAll(new VehicleTypeFilter()));
    }

    private VehicleFilter constructFilter(VehicleFilterBean filterBean) {
        VehicleFilter filter = new VehicleFilter();

        if (StringUtils.isNotBlank(filterBean.getBrand())) {
            filter.setBrand(filterBean.getBrand());
        }

        if (StringUtils.isNotBlank(filterBean.getModel())) {
            filter.setModel(filterBean.getModel());
        }

        if (StringUtils.isNotBlank(filterBean.getNumberPlate())) {
            filter.setNumberPlate(filterBean.getNumberPlate());
        }

        if (filterBean.getVehicleType() != null) {
            filter.setVehicleTypeId(filterBean.getVehicleType()
                    .getId());
        }

        return filter;
    }

}
