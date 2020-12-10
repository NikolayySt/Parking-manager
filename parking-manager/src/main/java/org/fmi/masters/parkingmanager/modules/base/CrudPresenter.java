package org.fmi.masters.parkingmanager.modules.base;

public interface CrudPresenter<BEAN> {

    void onAdd();

    void onEdit(BEAN selected);

    void onDelete(BEAN selected);

}
