package com.nerdygadgets.application.util.database;

import java.sql.*;
import java.util.Objects;

public final class RemoveDataFromDatabase {

        public static void deleteFromDatabase(long id, String type) {
            if (Objects.equals(type, "database")){
                type = "database1";
            }

            System.out.println(type);

            try {
                Connection connection = ConnectionToDatabase.DatabaseConnection();

                String sql = String.format("DELETE FROM %s WHERE id = ?", type);

                PreparedStatement preparedStmt = connection.prepareStatement(sql);
                //preparedStmt.setString (1, type);
                preparedStmt.setLong (1, id);

                preparedStmt.execute();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
