package org.fmi.masters.parkingmanager.modules.driver.ui.filter;


import java.util.Collection;
import java.util.Collections;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class DriverModuleFilter extends ModuleFilter<Driver, DriverFilterBean> implements DriverFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<Driver, DriverFilterBean> searchPresenter;
    private Presenter filterPresenter;

    private final TextField firstNameField = new TextField("First name");
    private final TextField lastNameField = new TextField("Last name");
    private final ComboBox<TextVo> vehicleCombo = new ComboBox<>("Vehicle");

    public DriverModuleFilter() {
        constuctFilterLayout();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        filterPresenter.setView(this);
        filterPresenter.loadFilters();
    }

    @Override
    public void setVehicles(Collection<Vehicle> vehicles) {
        vehicleCombo.setItems(vehicles.stream()
                .map(dto -> new TextVo(dto.getId(), constructVehicleText(dto))));
    }

    @Override
    protected HorizontalLayout getFields() {
        vehicleCombo.setItems(Collections.singletonList(TextVo.empty));
        vehicleCombo.setItemLabelGenerator(TextVo::getText);

        return new HorizontalLayout(firstNameField, lastNameField, vehicleCombo);
    }

    @Override
    protected void clearFields() {
        binder.setBean(new DriverFilterBean());
    }

    @Override
    protected SearchPresenter<Driver, DriverFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        binder.forField(firstNameField)
                .bind(DriverFilterBean::getFirstName, DriverFilterBean::setFirstName);

        binder.forField(lastNameField)
                .bind(DriverFilterBean::getLastName, DriverFilterBean::setLastName);

        binder.forField(vehicleCombo)
                .bind(DriverFilterBean::getVehicle, DriverFilterBean::setVehicle);

        binder.setBean(new DriverFilterBean());
    }

    public void setSearchPresenter(SearchPresenter<Driver, DriverFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void setFilterPresenter(Presenter filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    private String constructVehicleText(Vehicle dto) {
       StringBuilder builder = new StringBuilder();
       builder.append(dto.getBrand());
       builder.append(dto.getModel());
       builder.append("(");
       builder.append(dto.getNumberPlate());
       builder.append(")");
       
       return builder.toString();
    }

}
