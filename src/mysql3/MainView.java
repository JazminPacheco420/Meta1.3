package mysql3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainView extends Application {

    private TextField nombreField;
    private TextField direccionField;
    private TextField telefonoField;
    private TextField vehiculoField;
    private TextField idBajaField;
    private TableView<Contact> tableView;
    private ContactController controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new ContactController();

        // Campos de texto
        nombreField = new TextField();
        direccionField = new TextField();
        telefonoField = new TextField();
        vehiculoField = new TextField();
        idBajaField = new TextField();

        // Botones
        Button altaButton = new Button("Dar de alta");
        altaButton.setOnAction(e -> controller.insertarNuevoContacto(
                nombreField.getText(),
                direccionField.getText(),
                telefonoField.getText(),
                vehiculoField.getText(),
                tableView
        ));

        Button bajaButton = new Button("Dar de baja");
        bajaButton.setOnAction(e -> controller.eliminarContactoPorID(idBajaField.getText(), tableView));

        Button actualizarButton = new Button("Actualizar");
        actualizarButton.setOnAction(e -> controller.actualizarContacto(
                idBajaField.getText(),
                nombreField.getText(),
                direccionField.getText(),
                telefonoField.getText(),
                vehiculoField.getText(),
                tableView
        ));

        // Configuración de la tabla
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Contact, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Contact, String> nombreColumn = new TableColumn<>("Nombre");
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        TableColumn<Contact, String> direccionColumn = new TableColumn<>("Dirección");
        direccionColumn.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());

        TableColumn<Contact, String> telefonosColumn = new TableColumn<>("Teléfonos");
        telefonosColumn.setCellValueFactory(cellData -> cellData.getValue().telefonosProperty());

        TableColumn<Contact, String> vehiculosColumn = new TableColumn<>("Vehículos");
        vehiculosColumn.setCellValueFactory(cellData -> cellData.getValue().vehiculosProperty());

        tableView.getColumns().addAll(idColumn, nombreColumn, direccionColumn, telefonosColumn, vehiculosColumn);

        // Listener para cargar datos en los campos al seleccionar una fila
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Contact selectedContact = newValue;
                nombreField.setText(selectedContact.getNombre());
                direccionField.setText(selectedContact.getDireccion());
                telefonoField.setText(selectedContact.getTelefonos());
                vehiculoField.setText(selectedContact.getVehiculos());
                idBajaField.setText(String.valueOf(selectedContact.getId()));
            }
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1);
        grid.add(direccionField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(telefonoField, 1, 2);
        grid.add(new Label("Vehículo:"), 0, 3);
        grid.add(vehiculoField, 1, 3);
        grid.add(altaButton, 2, 3);
        grid.add(actualizarButton, 2, 4);

        grid.add(new Label("ID Baja:"), 0, 5);
        grid.add(idBajaField, 1, 5);
        grid.add(bajaButton, 2, 5);

        grid.add(tableView, 0, 6, 3, 1);

        // Escena
        Scene scene = new Scene(grid, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestión de Contactos");
        primaryStage.show();

        // Cargar datos iniciales
        controller.cargarDatos(tableView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}