package mysql3;

import javafx.scene.control.TableView; // Importación correcta de TableView
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importación de Statement
import javax.swing.JOptionPane;
import java.sql.Connection;
import mysql3.Mysql;


public class ContactController {

    public void cargarDatos(TableView<Contact> tableView) {
        try (Connection conn = Mysql.conectar()) {
            if (conn != null) {
                String query = "SELECT p.id, p.nombre, p.direccion, "
                        + "GROUP_CONCAT(DISTINCT t.telefono SEPARATOR ', ') AS telefonos, "
                        + "GROUP_CONCAT(DISTINCT v.vehiculo SEPARATOR ', ') AS vehiculos "
                        + "FROM personas p "
                        + "LEFT JOIN telefonos t ON p.id = t.persona_id "
                        + "LEFT JOIN vehiculos v ON p.id = v.persona_id "
                        + "GROUP BY p.id";

                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                tableView.getItems().clear();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String telefonos = rs.getString("telefonos");
                    String vehiculos = rs.getString("vehiculos");

                    tableView.getItems().add(new Contact(id, nombre, direccion, telefonos, vehiculos));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarNuevoContacto(String nombre, String direccion, String telefono, String vehiculo, TableView<Contact> tableView) {
        if (!nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !vehiculo.isEmpty()) {
            try (Connection conn = Mysql.conectar()) {
                String queryPersonas = "INSERT INTO personas (nombre, direccion) VALUES (?, ?)";
                PreparedStatement stmtPersonas = conn.prepareStatement(queryPersonas, Statement.RETURN_GENERATED_KEYS);
                stmtPersonas.setString(1, nombre);
                stmtPersonas.setString(2, direccion);
                stmtPersonas.executeUpdate();

                ResultSet rs = stmtPersonas.getGeneratedKeys();
                int personaId = 0;
                if (rs.next()) {
                    personaId = rs.getInt(1);
                }

                // Insertar teléfono
                String queryTelefonos = "INSERT INTO telefonos (persona_id, telefono) VALUES (?, ?)";
                PreparedStatement stmtTelefonos = conn.prepareStatement(queryTelefonos);
                stmtTelefonos.setInt(1, personaId);
                stmtTelefonos.setString(2, telefono);
                stmtTelefonos.executeUpdate();

                // Insertar vehículo
                String queryVehiculos = "INSERT INTO vehiculos (persona_id, vehiculo) VALUES (?, ?)";
                PreparedStatement stmtVehiculos = conn.prepareStatement(queryVehiculos);
                stmtVehiculos.setInt(1, personaId);
                stmtVehiculos.setString(2, vehiculo);
                stmtVehiculos.executeUpdate();

                JOptionPane.showMessageDialog(null, "Contacto insertado");

                cargarDatos(tableView);  // Recargar los datos en la tabla
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");
        }
    }

    public void actualizarContacto(String idTexto, String nombre, String direccion, String telefono, String vehiculo, TableView<Contact> tableView) {
        if (idTexto.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || vehiculo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            try (Connection conn = Mysql.conectar()) {
                // Actualizar los datos en la tabla personas
                String queryPersonas = "UPDATE personas SET nombre = ?, direccion = ? WHERE id = ?";
                PreparedStatement stmtPersonas = conn.prepareStatement(queryPersonas);
                stmtPersonas.setString(1, nombre);
                stmtPersonas.setString(2, direccion);
                stmtPersonas.setInt(3, id);
                stmtPersonas.executeUpdate();

                // Actualizar los teléfonos
                String queryTelefonos = "UPDATE telefonos SET telefono = ? WHERE persona_id = ?";
                PreparedStatement stmtTelefonos = conn.prepareStatement(queryTelefonos);
                stmtTelefonos.setString(1, telefono);
                stmtTelefonos.setInt(2, id);
                stmtTelefonos.executeUpdate();

                // Actualizar los vehículos
                String queryVehiculos = "UPDATE vehiculos SET vehiculo = ? WHERE persona_id = ?";
                PreparedStatement stmtVehiculos = conn.prepareStatement(queryVehiculos);
                stmtVehiculos.setString(1, vehiculo);
                stmtVehiculos.setInt(2, id);
                stmtVehiculos.executeUpdate();

                JOptionPane.showMessageDialog(null, "Contacto actualizado con éxito.");

                cargarDatos(tableView); // Recargar los datos en la tabla
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el contacto: " + e.getMessage());
        }
    }

    public void eliminarContactoPorID(String idTexto, TableView<Contact> tableView) {
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID válido.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            try (Connection conn = Mysql.conectar()) {
                // Eliminar teléfono
                String queryTelefonos = "DELETE FROM telefonos WHERE persona_id = ?";
                PreparedStatement stmtTelefonos = conn.prepareStatement(queryTelefonos);
                stmtTelefonos.setInt(1, id);
                stmtTelefonos.executeUpdate();

                // Eliminar vehículo
                String queryVehiculos = "DELETE FROM vehiculos WHERE persona_id = ?";
                PreparedStatement stmtVehiculos = conn.prepareStatement(queryVehiculos);
                stmtVehiculos.setInt(1, id);
                stmtVehiculos.executeUpdate();

                // Eliminar persona
                String queryPersonas = "DELETE FROM personas WHERE id = ?";
                PreparedStatement stmtPersonas = conn.prepareStatement(queryPersonas);
                stmtPersonas.setInt(1, id);
                stmtPersonas.executeUpdate();

                JOptionPane.showMessageDialog(null, "Contacto eliminado con éxito.");

                cargarDatos(tableView); // Recargar los datos en la tabla
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el contacto: " + e.getMessage());
        }
    }
}