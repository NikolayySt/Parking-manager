package org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ParkingPlaceModuleFilter extends ModuleFilter<ParkingPlace, ParkingPlaceFilterBean> implements ParkingPlaceFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<ParkingPlace, ParkingPlaceFilterBean> searchPresenter;

    private Presenter filterPresenter;

    private final TextField nameField = new TextField("Name");
    private final ComboBox<TextVo> parkingCombo = new ComboBox<>("Parking");
    private final ComboBox<TextVo> driverCombo = new ComboBox<>("Driver");
    private final ComboBox<TextVo> placeTypeCombo = new ComboBox<>("Place type combo");

    public ParkingPlaceModuleFilter() {
        constuctFilterLayout();
    }

    @PostConstruct
    public void init() {
        binder.setBean(new ParkingPlaceFilterBean());
        filterPresenter.loadFilters();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        filterPresenter.loadFilters();
    }

    @Override
    protected HorizontalLayout getFields() {
        parkingCombo.setItemLabelGenerator(TextVo::getText);
        driverCombo.setItemLabelGenerator(TextVo::getText);
        placeTypeCombo.setItemLabelGenerator(TextVo::getText);

        return new HorizontalLayout(nameField, parkingCombo, placeTypeCombo, driverCombo);
    }

    @Override
    protected SearchPresenter<ParkingPlace, ParkingPlaceFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        parkingCombo.setItems(Collections.singletonList(TextVo.empty));
        driverCombo.setItems(Collections.singletonList(TextVo.empty));
        placeTypeCombo.setItems(Collections.singletonList(TextVo.empty));

        binder.forField(nameField)
                .bind(ParkingPlaceFilterBean::getName, ParkingPlaceFilterBean::setName);
        binder.forField(parkingCombo)
                .bind(ParkingPlaceFilterBean::getParking, ParkingPlaceFilterBean::setParking);
        binder.forField(driverCombo)
                .bind(ParkingPlaceFilterBean::getDriver, ParkingPlaceFilterBean::setDriver);
        binder.forField(placeTypeCombo)
                .bind(ParkingPlaceFilterBean::getPlaceType, ParkingPlaceFilterBean::setPlaceType);

        binder.setBean(new ParkingPlaceFilterBean());
    }

    @Override
    protected void clearFields() {
        binder.setBean(new ParkingPlaceFilterBean());
    }

    public void setSearchPresenter(SearchPresenter<ParkingPlace, ParkingPlaceFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void setFilterPresenter(Presenter filterPresenter) {
        this.filterPresenter = filterPresenter;
        this.filterPresenter.setView(this);
    }

    @Override
    public void setParkings(Collection<Parking> parkings) {
        parkingCombo.setItems(parkings.stream()
                .map(dto -> new TextVo(dto.getId(), dto.getName()))
                .collect(Collectors.toList()));
    }

    @Override
    public void setDrivers(Collection<Driver> drivers) {
        driverCombo.setItems(drivers.stream()
                .map(dto -> new TextVo(dto.getId(), dto.getFirstName() + ", " + dto.getLastName()))
                .collect(Collectors.toList()));
    }

    @Override
    public void setPlaceTypes(Collection<ParkingPlaceType> placeTypes) {
        placeTypeCombo.setItems(placeTypes.stream()
                .map(dto -> new TextVo(dto.getId(), dto.getName()))
                .collect(Collectors.toList()));
    }

}
