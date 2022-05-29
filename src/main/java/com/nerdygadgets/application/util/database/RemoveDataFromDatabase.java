package com.nerdygadgets.application.util.database;

import java.sql.*;
import java.util.Objects;

public final class RemoveDataFromDatabase {

        public static void deleteFromDatabase(String id, String type) {
            if (Objects.equals(type, "database")){
                type = "database1";
            }

            try {
                Connection connection = ConnectionToDatabase.DatabaseConnection();

                String sql = "DELETE FROM ? WHERE id = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(sql);
                preparedStmt.setString (1, type);
                preparedStmt.setString (2, id);

                preparedStmt.execute();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
