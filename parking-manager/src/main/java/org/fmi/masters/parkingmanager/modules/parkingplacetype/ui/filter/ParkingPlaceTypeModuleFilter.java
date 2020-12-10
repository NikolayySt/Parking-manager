package org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ParkingPlaceTypeModuleFilter extends ModuleFilter<ParkingPlaceType, ParkingPlaceTypeFilterBean> implements ParkingPlaceTypeFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<ParkingPlaceType, ParkingPlaceTypeFilterBean> searchPresenter;
    private Presenter filterPresenter;

    private final TextField nameField = new TextField("Name");
    private final ComboBox<TextVo> vehicleTypeCombo = new ComboBox<>("Vehicle type");

    public ParkingPlaceTypeModuleFilter() {
        constuctFilterLayout();
    }

    @Override
    protected HorizontalLayout getFields() {
        vehicleTypeCombo.setItems(Collections.singletonList(TextVo.empty));

        vehicleTypeCombo.setItemLabelGenerator(TextVo::getText);

        return new HorizontalLayout(nameField, vehicleTypeCombo);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        filterPresenter.setView(this);
        filterPresenter.loadFilters();
    }

    @Override
    protected void clearFields() {
        binder.setBean(new ParkingPlaceTypeFilterBean());
    }

    @Override
    protected SearchPresenter<ParkingPlaceType, ParkingPlaceTypeFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        binder.forField(nameField)
                .bind(ParkingPlaceTypeFilterBean::getName, ParkingPlaceTypeFilterBean::setName);
        binder.forField(vehicleTypeCombo)
                .bind(ParkingPlaceTypeFilterBean::getVehicleType, ParkingPlaceTypeFilterBean::setVehicleType);
        

        binder.setBean(new ParkingPlaceTypeFilterBean());
    }

    public void setSearchPresenter(SearchPresenter<ParkingPlaceType, ParkingPlaceTypeFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void setFilterPresenter(Presenter filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    @Override
    public void setVehicleTypes(Collection<VehicleType> vehicleTypes) {
        vehicleTypeCombo.setItems(vehicleTypes.stream()
                .map(dto -> new TextVo(dto.getId(), dto.getName()))
                .collect(Collectors.toList()));
    }

}
