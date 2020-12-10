package org.fmi.masters.parkingmanager.modules.driver.ui.dialog;

import java.util.Collection;
import java.util.function.Consumer;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.dto.Vehicle;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;

public class DriverDialog extends Dialog {

    private static final long serialVersionUID = 1L;

    private Consumer<Driver> onSave;

    private final Binder<Driver> binder = new Binder<>();

    private final Label idLabel = new Label();
    private final TextField firstNameField = new TextField("First name");
    private final TextField lastNameField = new TextField("Last name");
    private final ComboBox<Vehicle> vehiclesCombo = new ComboBox<Vehicle>("Vehicles");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    public DriverDialog(Driver bean, Collection<Vehicle> vehicles) {
        vehiclesCombo.setItems(vehicles);
        vehiclesCombo.setItemLabelGenerator(item -> constructVehicleText(item));

        binder.setBean(bean);
        bindFields();
        setWidth("300px");
        setHeight(null);


        boolean isEdit = bean.getId() != null;
        add(isEdit ? "Edit driver" : "Add driver");
        idLabel.setText(isEdit ? "ID: " + bean.getId() : "ID: --");
        add(new VerticalLayout(idLabel, firstNameField, lastNameField, vehiclesCombo, constructButtonsLayout()));
    }

    private void bindFields() {
        binder.forField(
                firstNameField)
                .asRequired()
                .bind(Driver::getFirstName, Driver::setFirstName);

        binder.forField(
                lastNameField)
                .asRequired()
                .asRequired()
                .bind(Driver::getLastName, Driver::setLastName);

        binder.forField(
                vehiclesCombo)
                .asRequired()
                .bind(Driver::getVehicle, Driver::setVehicle);
    }

    public void setOnSave(Consumer<Driver> onSave) {
        this.onSave = onSave;
    }

    private Component constructButtonsLayout() {
        saveButton.addClickListener(event -> save());
        cancelButton.addClickListener(event -> close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidthFull();

        return buttonsLayout;
    }

    private void save() {
        BinderValidationStatus<Driver> validationStatus = binder.validate();
        if (validationStatus.isOk()) {
            onSave.accept(binder.getBean());
            close();
        }
        else {
            Notification.show("Fill all required fields", 1000, Position.MIDDLE);
        }
    }

    private String constructVehicleText(Vehicle item) {
        StringBuilder builder = new StringBuilder();
        builder.append(item.getBrand());
        builder.append(" ");
        builder.append(item.getModel());
        builder.append(" (");
        builder.append(item.getNumberPlate());
        builder.append(")");

        return builder.toString();
    }

}
