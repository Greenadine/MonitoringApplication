package com.nerdygadgets.application.util.database;

import com.nerdygadgets.application.model.component.ComponentType;
import com.nerdygadgets.application.model.component.NetworkComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public final class EditDataFromDatabase {

    public static void editComponent(NetworkComponent component) {
        String type = component.getType().name().toLowerCase();
        if (component.getType() == ComponentType.DATABASE){
            type = "database1";
        }

        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "UPDATE ? SET name = ?, availability = ?, price = ?, ip = ?, subnetmask = ? WHERE id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, type);
            preparedStmt.setString (2, component.getName());
            preparedStmt.setDouble (3, component.getAvailability());
            preparedStmt.setDouble (4, component.getPrice());
            preparedStmt.setString (5, component.getIp());
            preparedStmt.setString (6, component.getSubnetMask());
            preparedStmt.setLong (7, component.getId());

            preparedStmt.execute();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
