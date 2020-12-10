package org.fmi.masters.parkingmanager.modules.vehicle.ui.dialog;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.dto.VehicleType;

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

public class VehicleDialog extends Dialog {

    private static final long serialVersionUID = 1L;

    private Consumer<Vehicle> onSave;
    private Predicate<String> numberPlateValidator;

    private final Binder<Vehicle> binder = new Binder<>();

    private final Label idLabel = new Label();
    private final TextField brandField = new TextField("Brand");
    private final TextField modelField = new TextField("Model");
    private final TextField numberPlateField = new TextField("Number plate");
    private final ComboBox<VehicleType> vehicleTypeCombo = new ComboBox<VehicleType>("Vehicle type");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final String initialNumberPlate;

    public VehicleDialog(Vehicle bean, Collection<VehicleType> vehicleTypes) {
        initialNumberPlate = bean.getNumberPlate();

        vehicleTypeCombo.setItems(vehicleTypes);
        vehicleTypeCombo.setItemLabelGenerator(VehicleType::getName);

        binder.setBean(bean);
        bindFields();
        setWidth("300px");
        setHeight(null);


        boolean isEdit = bean.getId() != null;
        add(isEdit ? "Edit vehicle" : "Add vehicle");
        idLabel.setText(isEdit ? "ID: " + bean.getId() : "ID: --");
        add(new VerticalLayout(idLabel, brandField, modelField, numberPlateField, vehicleTypeCombo, constructButtonsLayout()));
    }

    private void bindFields() {
        binder.forField(brandField)
                .asRequired()
                .bind(Vehicle::getBrand, Vehicle::setBrand);

        binder.forField(modelField)
                .asRequired()
                .bind(Vehicle::getModel, Vehicle::setModel);

        binder.forField(numberPlateField)
                .asRequired()
                .asRequired()
                .bind(Vehicle::getNumberPlate, Vehicle::setNumberPlate);

        binder.forField(vehicleTypeCombo)
                .asRequired()
                .bind(Vehicle::getVehicleType, Vehicle::setVehicleType);
    }

    public void setOnSave(Consumer<Vehicle> onSave) {
        this.onSave = onSave;
    }

    public void setNumberPlateValidator(Predicate<String> numberPlateValidator) {
        this.numberPlateValidator = numberPlateValidator;
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
        BinderValidationStatus<Vehicle> validationStatus = binder.validate();
        if (validationStatus
                .isOk()) {
            Vehicle bean = binder.getBean();
            if (!Objects.equals(initialNumberPlate, bean.getNumberPlate()) && !numberPlateValidator.test(bean
                    .getNumberPlate())) {
                Notification.show("Number plate is already taken!", 1000, Position.MIDDLE);
                return;
            }

            onSave.accept(bean);
            close();
        }
        else {
            Notification.show("Fill all required fields", 1000, Position.MIDDLE);
        }
    }

}
