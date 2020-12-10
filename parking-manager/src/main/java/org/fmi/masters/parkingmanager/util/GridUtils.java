package org.fmi.masters.parkingmanager.util;

import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.grid.Grid;

public final class GridUtils {

    private GridUtils() {
        /* No instances needed */
    }

    public static <T> T getSelected(Grid<T> grid) {
        return CollectionUtils.isEmpty(grid.getSelectedItems()) ? null
                : grid.getSelectedItems()
                        .iterator()
                        .next();
    }

}
