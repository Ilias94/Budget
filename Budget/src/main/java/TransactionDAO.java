import java.sql.*;
import java.time.LocalDate;

public class TransactionDAO {


    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Sterownik do bazy nie został znaleziony!");
            return null;
        }
        String url = "jdbc:mysql://localhost:3306/transactions?serverTimezone=UTC&characterEncoding=utf8";
        try {
            return DriverManager.getConnection(url, "root", "bondiILIAS321");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    void add(Transaction transaction) {
        Connection connection = connect();

        PreparedStatement preparedStatement = null;

        try {
            final String sql = "INSERT INTO transaction (type, description, amount, date) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Niepowodzenie podczas zapisu do bazy!");
        }
        closeConnection(connection);
    }

    void update(Transaction transaction) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;

        try {
            final String sql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate()));
            preparedStatement.setInt(5, transaction.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Niepowodzenie podczas modyfikacji");
        }
        closeConnection(connection);
    }

    void deleteById(int id) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;

        try {
            final String sql = "DELETE FROM transaction WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(connection);
    }

    public void displayAllIncome() {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;

        try {
            final String sql = " SELECT * FROM transaction WHERE type = 'Przychód'";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                System.out.printf("%d | %-5s | %-5s | %-5.2fzł | %tm/%td/%tY", id, type, description, amount, date, date, date);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(connection);
    }

    public void displayAllExpenses() {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;

        try {
            final String sql = " SELECT * FROM transaction WHERE type = 'Wydatek'";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                System.out.printf("%d | %-5s | %-5s | %-5.2fzł | %tm/%td/%tY", id, type, description, amount, date, date, date);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(connection);
    }
}
