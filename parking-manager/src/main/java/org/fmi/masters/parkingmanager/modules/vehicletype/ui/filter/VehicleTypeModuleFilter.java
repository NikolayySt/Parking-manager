package org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VehicleTypeModuleFilter extends ModuleFilter<VehicleType, VehicleTypeFilterBean> implements VehicleTypeFilterView {

    private static final long serialVersionUID = 1L;

    private SearchPresenter<VehicleType, VehicleTypeFilterBean> searchPresenter;
    private Presenter filterPresenter;

    private final TextField nameField = new TextField("Name");

    public VehicleTypeModuleFilter() {
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
        return new HorizontalLayout(nameField);
    }

    @Override
    protected void clearFields() {
        binder.setBean(new VehicleTypeFilterBean());
    }

    @Override
    protected SearchPresenter<VehicleType, VehicleTypeFilterBean> getSearchPresenter() {
        return searchPresenter;
    }

    @Override
    protected void bindFields() {
        binder.forField(nameField)
                .bind(VehicleTypeFilterBean::getName, VehicleTypeFilterBean::setName);

        binder.setBean(new VehicleTypeFilterBean());
    }

    public void setFilterPresenter(Presenter filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    public void setSearchPresenter(SearchPresenter<VehicleType, VehicleTypeFilterBean> searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

}
