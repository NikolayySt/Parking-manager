package org.fmi.masters.parkingmanager.modules.parking.ui.dialog;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.fmi.masters.parkingmanager.dto.Parking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;

public class ParkingDialog extends Dialog {

    private static final long serialVersionUID = 1L;

    private Consumer<Parking> onSave;
    private Predicate<String> nameValidator;

    private final Binder<Parking> binder = new Binder<>();

    private final Label idLabel = new Label();
    private final TextField nameField = new TextField("Name");
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private String initialName;

    public ParkingDialog(Parking bean) {
        this.initialName = bean.getName();

        binder.setBean(bean);
        bindFields();
        setWidth("300px");
        setHeight(null);


        boolean isEdit = bean.getId() != null;
        add(isEdit ? "Edit parking" : "Add parking");
        idLabel.setText(isEdit ? "ID: " + bean.getId() : "ID: --");
        add(new VerticalLayout(idLabel, nameField, constructButtonsLayout()));
    }

    private void bindFields() {
        binder.forField(nameField)
                .asRequired()
                .bind(Parking::getName, Parking::setName);
    }

    public void setOnSave(Consumer<Parking> onSave) {
        this.onSave = onSave;
    }

    public void setNameValidator(Predicate<String> nameValidator) {
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

    private void save() {
        BinderValidationStatus<Parking> validationStatus = binder.validate();
        if (validationStatus.isOk()) {
            Parking bean = binder.getBean();
            if (!Objects.equals(initialName, bean.getName()) && !nameValidator.test(bean.getName())) {
                Notification.show("Name is already taken", 1000, Position.MIDDLE);
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
