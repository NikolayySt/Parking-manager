package org.fmi.masters.parkingmanager.modules.vehicle.ui.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VehicleModuleFilter extends ModuleFilter<Vehicle, VehicleFilterBean> implements VehicleFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<Vehicle, VehicleFilterBean> searchPresenter;
    private Presenter filterPresenter;

    private final TextField brandField = new TextField("Brand");
    private final TextField modelField = new TextField("Model");
    private final TextField numberPlateField = new TextField("Number plate");
    private final ComboBox<TextVo> vehicleTypeCombo = new ComboBox<>("Vehicle type");

    public VehicleModuleFilter() {
        constuctFilterLayout();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        filterPresenter.setView(this);
        filterPresenter.loadFilters();
    }

    @Override
    protected HorizontalLayout getFields() {
        vehicleTypeCombo.setItems(Collections.singletonList(TextVo.empty));
        vehicleTypeCombo.setItemLabelGenerator(TextVo::getText);

        return new HorizontalLayout(brandField, modelField, numberPlateField, vehicleTypeCombo);
    }

    @Override
    protected void clearFields() {
        binder.setBean(new VehicleFilterBean());
    }

    @Override
    protected SearchPresenter<Vehicle, VehicleFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        binder.forField(brandField)
                .bind(VehicleFilterBean::getBrand, VehicleFilterBean::setBrand);
        binder.forField(modelField)
                .bind(VehicleFilterBean::getModel, VehicleFilterBean::setModel);
        binder.forField(numberPlateField)
                .bind(VehicleFilterBean::getNumberPlate, VehicleFilterBean::setNumberPlate);
        binder.forField(vehicleTypeCombo)
                .bind(VehicleFilterBean::getVehicleType, VehicleFilterBean::setVehicleType);

        binder.setBean(new VehicleFilterBean());
    }

    @Override
    public void setVehicleTypes(Collection<VehicleType> vehicleTypes) {
        vehicleTypeCombo.setItems(vehicleTypes.stream()
                .map(dto -> new TextVo(dto.getId(), dto.getName()))
                .collect(Collectors.toList()));
    }

    public void setFilterPresenter(Presenter filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    public void setSearchPresenter(SearchPresenter<Vehicle, VehicleFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

}
