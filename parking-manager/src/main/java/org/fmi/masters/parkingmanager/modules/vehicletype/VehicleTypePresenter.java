package org.fmi.masters.parkingmanager.modules.vehicletype;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.VehicleTypeView;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.dialog.VehicleTypeDialog;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeFilterView;
import org.fmi.masters.parkingmanager.service.VehicleService;
import org.fmi.masters.parkingmanager.service.VehicleTypeService;
import org.fmi.masters.parkingmanager.service.filter.VehicleFilter;
import org.fmi.masters.parkingmanager.service.filter.VehicleTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class VehicleTypePresenter implements VehicleTypeView.Presenter {

    @Resource
    private VehicleTypeService vehicleTypeService;

    @Resource
    private VehicleService vehicleService;

    private VehicleTypeView view;

    private VehicleTypeFilterView filterView;

    private VehicleTypeFilterBean currentFilter;

    @Override
    public void onSubmit(VehicleTypeFilterBean filterBean) {
        if (filterBean != null) {
            currentFilter = filterBean;
        }
        else {
            currentFilter = new VehicleTypeFilterBean();
        }

        Collection<VehicleType> results = vehicleTypeService.findAll(constructFilter(filterBean));

        if (CollectionUtils.isEmpty(results)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setVehicleTypes(results);
    }

    private VehicleTypeFilter constructFilter(VehicleTypeFilterBean filterBean) {
        VehicleTypeFilter filter = new VehicleTypeFilter();

        if (StringUtils.isNotBlank(filterBean.getName())) {
            filter.setName(filterBean.getName());
        }

        return filter;
    }

    @Override
    public void setView(VehicleTypeView view) {
        this.view = view;
    }

    @Override
    public void onAdd() {
        VehicleTypeDialog dialog = new VehicleTypeDialog(new VehicleType());
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onEdit(VehicleType selected) {
        if (selected == null) {
            Notification.show("Please select a vehicle type in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        VehicleTypeDialog dialog = new VehicleTypeDialog(selected);
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onDelete(VehicleType selected) {
        if (selected == null) {
            Notification.show("Please select a vehicle type in order to delete.", 1000, Position.MIDDLE);
            return;
        }

        Long vehicleTypeId = selected.getId();

        VehicleFilter vehicleFilter = new VehicleFilter();
        vehicleFilter.setVehicleTypeId(vehicleTypeId);
        if (!CollectionUtils.isEmpty(vehicleService.findAll(vehicleFilter))) {
            Notification.show("Deletion of this vehicle type is not allowed. It`s connected to vehicle record.", 3000, Position.MIDDLE);
            return;
        }
        
        vehicleTypeService.deleteById(vehicleTypeId);
        onSubmit(currentFilter);
    }

    private void onSave(VehicleType vehicleType) {
        vehicleTypeService.save(vehicleType);
        onSubmit(currentFilter);
    }

    private boolean isNameUnique(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        return vehicleTypeService.findByName(name.trim()) == null;
    }

    @Override
    public void setView(VehicleTypeFilterView view) {
        this.filterView = view;
    }

    @Override
    public void loadFilters() {
        /* Nothing to load */
    }

}
