package mysql3;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty direccion;
    private final SimpleStringProperty telefonos;
    private final SimpleStringProperty vehiculos;

    public Contact(int id, String nombre, String direccion, String telefonos, String vehiculos) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.telefonos = new SimpleStringProperty(telefonos);
        this.vehiculos = new SimpleStringProperty(vehiculos);
    }

    // Getters para los valores
    public int getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getDireccion() {
        return direccion.get();
    }

    public String getTelefonos() {
        return telefonos.get();
    }

    public String getVehiculos() {
        return vehiculos.get();
    }

    // Métodos property() para JavaFX
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleStringProperty direccionProperty() {
        return direccion;
    }

    public SimpleStringProperty telefonosProperty() {
        return telefonos;
    }

    public SimpleStringProperty vehiculosProperty() {
        return vehiculos;
    }
}