package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import gym.modelo.Cliente;



public class ListaClientesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDireccion;
    private JTextField textoNombre, textoApellido, textoDireccion;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;

    public ListaClientesFrame() {
        super("Clientes");

        this.clienteController = new ClienteController();

        Container container = getContentPane();
        setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);

        configurarAccionesDelFormulario();
    }

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        
        //modelo.addColumn(new ImageIcon(ListaClientesFrame.class.getResource("/descargas/gym.png")));


        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonReporte = new JButton("Ver Reporte");
        botonEliminar.setBounds(10, 500, 80, 20);
        botonModificar.setBounds(100, 500, 80, 20);
        botonReporte.setBounds(190, 500, 80, 20);

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonReporte);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre");
        labelApellido = new JLabel("Apellido");
        labelDireccion = new JLabel("Direccion");

        labelNombre.setBounds(10, 10, 240, 15);
        labelApellido.setBounds(10, 50, 240, 15);
        labelDireccion.setBounds(10, 90, 240, 15);

        labelNombre.setForeground(Color.BLACK);
        labelApellido.setForeground(Color.BLACK);
        labelDireccion.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoApellido = new JTextField();
        textoDireccion = new JTextField();

        // TODO
        
        textoNombre.setBounds(10, 25, 265, 20);
        textoApellido.setBounds(10, 65, 265, 20);
        textoDireccion.setBounds(10, 105, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(10, 175, 80, 20);
        botonLimpiar.setBounds(100, 175, 80, 20);

        container.add(labelNombre);
        container.add(labelApellido);
        container.add(labelDireccion);
        container.add(textoNombre);
        container.add(textoApellido);
        container.add(textoDireccion);
        container.add(botonGuardar);
        container.add(botonLimpiar);
       
    }

    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					guardar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                limpiarTabla();
                cargarTabla();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	atualizar();          	
                limpiarTabla();
                cargarTabla();
            }
        });

        botonReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					abrirReporte();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    private void abrirReporte() throws SQLException {
        new ReporteClientes(this);
    }

    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }
    
    //*****************************************
    private void atualizar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                	String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
                    String apellido = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
                    String direccion = (String) modelo.getValueAt(tabla.getSelectedRow(), 3);
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

                    int cantidadActualizada;
                    try {
                    	cantidadActualizada = this.clienteController.actualizar(nombre, apellido, direccion, id);	
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, cantidadActualizada + " Item Modificado con éxito!");
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
    //****************************************
 
    
    private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

                    int cantidadEliminada;
                    try {
                    	cantidadEliminada = this.clienteController.eliminar(id);	
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, cantidadEliminada + " Item eliminado con éxito!");
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
    
    
    
    private void cargarTabla() {
        List<Map<String, String>> productos = new ArrayList<Map<String, String>>();

        try {
            productos = this.clienteController.listar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        productos.forEach(producto -> modelo.addRow(
                new Object[] {
                        producto.get("ID"),
                        producto.get("NOMBRE"),
                        producto.get("APELLIDO"),
                        producto.get("DIRECCION") }));
    }
        
    
    //------------------------------------------
    
    private void guardar() throws SQLException {
             
            Cliente cliente = new Cliente(textoNombre.getText(), textoApellido.getText(), textoDireccion.getText());
 			this.clienteController.guardar(cliente);
 			//dispose(); //me cierra el formulario automaticamente
 			
 			JOptionPane.showMessageDialog(this, "Registrado con éxito!");

 	        this.limpiarFormulario();
    							
    }
    
    //******************************************

   
    //formula limpiar fomulario
    private void limpiarFormulario() {
        this.textoNombre.setText("");
        this.textoApellido.setText("");
        this.textoDireccion.setText("");
    }

}
