package vista;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;


import javax.swing.JOptionPane;


public class RegistroIngresos extends JFrame {
	
	private JTextField textoApellido;
    private JButton botonBuscar;
    private JTable tablaClientes;
    private JCheckBox[] checkboxes;
    private JButton botonAsignarPago;

    public RegistroIngresos() {
        // Configuración de la ventana principal
        super("Asignación de Pagos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Creación de componentes
        textoApellido = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        tablaClientes = new JTable();
        botonAsignarPago = new JButton("Asignar Pago");

        // Configuración del modelo de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Pago");
        tablaClientes.setModel(modeloTabla);

        // Configuración del diseño de la ventana
        setLayout(new BorderLayout());

        // Panel para la búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Apellido:"));
        panelBusqueda.add(textoApellido);
        panelBusqueda.add(botonBuscar);

        // Panel para la tabla
        JScrollPane panelTabla = new JScrollPane(tablaClientes);

        // Panel para la asignación de pagos
        JPanel panelAsignacion = new JPanel();
        panelAsignacion.add(botonAsignarPago);

        // Adición de componentes a la ventana
        add(panelBusqueda, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelAsignacion, BorderLayout.SOUTH);

        // Configuración de acciones para el botón de búsqueda
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de búsqueda y actualización de la tabla
                buscarClientesPorApellido();
            }
        });

        // Configuración de acciones para el botón de asignación de pagos
        botonAsignarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de asignación de pagos
                asignarPagos();
            }
        });
    }

    private void buscarClientesPorApellido() {
        // Lógica de búsqueda (debes implementar esta lógica según tu aplicación)
        // Aquí se agrega un ejemplo de datos ficticios a la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla

        // Aquí deberías realizar la búsqueda en tu base de datos y agregar los resultados a la tabla
        // Ejemplo de datos ficticios
        Object[] fila1 = {1, "Juan", "Pérez", false};
        Object[] fila2 = {2, "María", "López", false};
        modeloTabla.addRow(fila1);
        modeloTabla.addRow(fila2);
    }

    private void asignarPagos() {
        // Lógica de asignación de pagos (debes implementar esta lógica según tu aplicación)
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            boolean seleccionado = (boolean) modeloTabla.getValueAt(i, 3);
            if (seleccionado) {
                // Aquí deberías realizar la asignación de pagos en tu base de datos
                // Puedes obtener el ID del cliente y realizar la acción correspondiente
                int idCliente = (int) modeloTabla.getValueAt(i, 0);
                // Implementa tu lógica de asignación de pagos aquí
                System.out.println("Pago asignado al cliente con ID " + idCliente);
            }
        }

        // Puedes mostrar un mensaje de éxito o realizar otras acciones después de asignar los pagos
        JOptionPane.showMessageDialog(this, "Pagos asignados con éxito");
    }

 
}