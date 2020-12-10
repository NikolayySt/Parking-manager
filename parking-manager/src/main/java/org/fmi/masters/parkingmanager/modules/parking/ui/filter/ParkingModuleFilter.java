package org.fmi.masters.parkingmanager.modules.parking.ui.filter;

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ParkingModuleFilter extends ModuleFilter<Parking, ParkingFilterBean> implements ParkingFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<Parking, ParkingFilterBean> searchPresenter;

    private final TextField nameField = new TextField("Name");

    public ParkingModuleFilter() {
        constuctFilterLayout();
    }

    @Override
    protected HorizontalLayout getFields() {
        return new HorizontalLayout(nameField);
    }

    @Override
    protected void clearFields() {
        binder.setBean(new ParkingFilterBean());
    }

    @Override
    protected SearchPresenter<Parking, ParkingFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        binder.forField(nameField)
                .bind(ParkingFilterBean::getName, ParkingFilterBean::setName);

        binder.setBean(new ParkingFilterBean());
    }

    public void setSearchPresenter(SearchPresenter<Parking, ParkingFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }


}
