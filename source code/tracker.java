import javafx.application.Application;
String sql = "INSERT INTO expenses(date, category, amount) VALUES(?,?,?)";


try (Connection conn = DriverManager.getConnection(DB_URL);
PreparedStatement ps = conn.prepareStatement(sql)) {


ps.setString(1, date);
ps.setString(2, category);
ps.setDouble(3, amount);
ps.executeUpdate();


} catch (SQLException e) {
e.printStackTrace();
}
}


private void loadExpenses() {
String sql = "SELECT * FROM expenses";


try (Connection conn = DriverManager.getConnection(DB_URL);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql)) {


while (rs.next()) {
data.add(new Expense(
rs.getString("date"),
rs.getString("category"),
String.valueOf(rs.getDouble("amount"))
));
}


} catch (SQLException e) {
e.printStackTrace();
}
}


private void updateChart(PieChart chart) {
chart.getData().clear();


String sql = "SELECT category, SUM(amount) as total FROM expenses GROUP BY category";


try (Connection conn = DriverManager.getConnection(DB_URL);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql)) {


while (rs.next()) {
chart.getData().add(new PieChart.Data(
rs.getString("category"),
rs.getDouble("total")
));
}


} catch (SQLException e) {
e.printStackTrace();
}
}


private void showAlert(String msg) {
Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
alert.showAndWait();
}
}


// ---- Expense Model Class ----
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


class Expense {
private final StringProperty date;
private final StringProperty category;
private final StringProperty amount;


public Expense(String date, String category, String amount) {
this.date = new SimpleStringProperty(date);
this.category = new SimpleStringProperty(category);
this.amount = new SimpleStringProperty(amount);
}


public StringProperty dateProperty() { return date; }
public StringProperty categoryProperty() { return category; }
public StringProperty amountProperty() { return amount; }
}
