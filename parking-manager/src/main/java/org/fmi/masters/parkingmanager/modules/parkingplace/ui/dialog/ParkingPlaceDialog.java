package org.fmi.masters.parkingmanager.modules.parkingplace.ui.dialog;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
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

public class ParkingPlaceDialog extends Dialog {

    private static final long serialVersionUID = 1L;

    private Consumer<ParkingPlace> onSave;
    private BiPredicate<String, Parking> nameValidator;

    private final Binder<ParkingPlace> binder = new Binder<>();

    private final Label idLabel = new Label();
    private final TextField nameField = new TextField("Name");
    private final ComboBox<Driver> driverCombo = new ComboBox<>("Driver");
    private final ComboBox<ParkingPlaceType> parkingPlaceTypeCombo = new ComboBox<>("Parking place type");
    private final ComboBox<Parking> parkingCombo = new ComboBox<>("Parking");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final String initialName;
    private final Parking initialParking;
    private final Collection<Driver> drivers;

    public ParkingPlaceDialog(ParkingPlace bean, Collection<Parking> parkings, Collection<ParkingPlaceType> parkingPlaceTypes, Collection<Driver> drivers) {
        initialName = bean.getName();
        initialParking = bean.getParking();
        this.drivers = drivers;

        parkingCombo.setItems(parkings);
        parkingCombo.setItemLabelGenerator(Parking::getName);
        
        driverCombo.setItems(drivers);
        driverCombo.setItemLabelGenerator(driver -> driver.getFirstName() + ", " + driver.getLastName());

        parkingPlaceTypeCombo.setItems(parkingPlaceTypes);
        parkingPlaceTypeCombo.setItemLabelGenerator(ParkingPlaceType::getName);
        parkingPlaceTypeCombo.addValueChangeListener(this::filterDriversCombo);

        binder.setBean(bean);
        bindFields();
        setWidth("300px");
        setHeight(null);


        boolean isEdit = bean.getId() != null;
        add(isEdit ? "Edit parking place" : "Add parking place");
        idLabel.setText(isEdit ? "ID: " + bean.getId() : "ID: --");
        add(new VerticalLayout(idLabel, nameField,parkingCombo, parkingPlaceTypeCombo, driverCombo, constructButtonsLayout()));
    }

    private void bindFields() {
        binder.forField(nameField)
                .asRequired()
                .bind(ParkingPlace::getName, ParkingPlace::setName);
       
        binder.forField(parkingCombo)
                .asRequired()
                .bind(ParkingPlace::getParking, ParkingPlace::setParking);

        binder.forField(driverCombo)
                .bind(ParkingPlace::getDriver, ParkingPlace::setDriver);

        binder.forField(
                parkingPlaceTypeCombo)
                .asRequired()
                .bind(ParkingPlace::getParkingPlaceType, ParkingPlace::setParkingPlaceType);
    }

    public void setOnSave(Consumer<ParkingPlace> onSave) {
        this.onSave = onSave;
    }

    public void setNameValidator(BiPredicate<String, Parking> nameValidator) {
        this.nameValidator = nameValidator;
    }

    private Component constructButtonsLayout() {
        saveButton.addClickListener(event -> save());
        cancelButton.addClickListener(event -> close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidthFull();

        return buttonsLayout;
    }

    private void filterDriversCombo(ValueChangeEvent<ParkingPlaceType> event) {
        ParkingPlaceType value = event.getValue();
        if (value == null) {
            driverCombo.setItems(drivers);
        }

        driverCombo.setItems(drivers.stream()
                .filter(driver -> value.getVehicleTypes()
                        .contains(driver.getVehicle()
                                .getVehicleType()))
                .collect(Collectors.toList()));
    }

    private void save() {
        BinderValidationStatus<ParkingPlace> validationStatus = binder.validate();
        if (validationStatus.isOk()) {
            ParkingPlace bean = binder.getBean();
            if ((!Objects.equals(initialName, bean.getName()) || !Objects.equals(initialParking, bean.getParking()))
                    && !nameValidator.test(bean.getName(), bean.getParking())) {
                Notification.show("Name is already taken for that parking", 1000, Position.MIDDLE);
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
