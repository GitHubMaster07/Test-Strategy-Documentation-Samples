package examples.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDbValidator {

    private final Connection connection;

    public UserDbValidator(Connection connection) {
        this.connection = connection;
    }

    public boolean userExists(String email) throws Exception {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}
