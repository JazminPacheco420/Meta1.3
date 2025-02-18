/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysql3;

/**
 *
 * @author andre
 */


public class Main {
    public static void main(String[] args) {
        ContactController contactController = new ContactController();
        
        System.out.println("Cargando datos...");
        // Puedes probar cargar datos en la consola (requiere que la tabla ya tenga registros)
        contactController.cargarDatos(null);

        // Insertar un contacto de prueba (descomentar si quieres probar)
        // contactController.insertarNuevoContacto("Juan Perez", "Calle Falsa 123", "555-1234", "Toyota", null);

        System.out.println("Programa finalizado.");
    }
}

