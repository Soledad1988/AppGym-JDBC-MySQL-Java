package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.GastosController;
import controller.ClienteController;
import gym.modelo.Cliente;
import gym.modelo.Gastos;

import java.sql.Date;
import javax.swing.JComboBox;



public class AltaGastosFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombreGasto, labelTipo, labelCosto;
    private JTextField textoNombreGasto, textoTipo, textoCosto;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonMenu;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> BoxPeriodo;
    private GastosController gastoController;
    private MenuFrame menuFrame;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaGastosFrame frame = new AltaGastosFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public AltaGastosFrame() throws SQLException {
        super("Gastos");

        this.gastoController = new GastosController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        
        JLabel labelFechaGasto = new JLabel("Fecha Gasto");
        labelFechaGasto.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelFechaGasto.setForeground(Color.BLACK);
        labelFechaGasto.setBounds(35, 52, 240, 15);
        getContentPane().add(labelFechaGasto);
        
        JLabel lblTitulo = new JLabel("Gastos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(325, 0, 213, 30);
        getContentPane().add(lblTitulo);

        BoxPeriodo = new JComboBox<>();
        BoxPeriodo.setModel(new DefaultComboBoxModel<>(new String[]{"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
        BoxPeriodo.setBounds(345, 48, 265, 22);
        getContentPane().add(BoxPeriodo);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Id");
        modelo.addColumn("Perido Gasto");
        modelo.addColumn("Gasto");
        modelo.addColumn("Tipo");
        modelo.addColumn("Costo");


        cargarTabla();

        tabla.setBounds(10, 286, 760, 143);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonMenu = new JButton("Menú");
        
        botonEliminar.setBounds(254, 458, 80, 20);
        botonModificar.setBounds(344, 458, 80, 20);
        botonMenu.setBounds(434, 458, 80, 20);
        

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonMenu);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombreGasto = new JLabel("Gasto");
        labelNombreGasto.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelTipo = new JLabel("Tipo");
        labelTipo.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelCosto = new JLabel("Costo");
        labelCosto.setFont(new Font("Tahoma", Font.BOLD, 12));

        labelNombreGasto.setBounds(35, 102, 240, 15);
        labelTipo.setBounds(35, 148, 240, 15);
        labelCosto.setBounds(35, 194, 240, 15);
        
        labelNombreGasto.setForeground(Color.BLACK);
        labelTipo.setForeground(Color.BLACK);
        labelCosto.setForeground(Color.BLACK);

        textoNombreGasto = new JTextField();
        textoTipo = new JTextField();
        textoCosto = new JTextField();

        // TODO
        
        textoNombreGasto.setBounds(345, 100, 265, 20);
        textoTipo.setBounds(345, 148, 265, 20);
        textoCosto.setBounds(345, 197, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(288, 262, 80, 20);
        botonLimpiar.setBounds(378, 262, 80, 20);

        container.add(labelNombreGasto);
        container.add(labelTipo);
        container.add(labelCosto);
        container.add(textoNombreGasto);
        container.add(textoTipo);
        container.add(textoCosto);
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
            	actualizar();
            }
            
        });
        
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	volverMenu();          	
            }
            
        });

    }

    
    private void volverMenu() {
        // Cerrar la ventana actual
        dispose();

        // Crear y mostrar la instancia del menú
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }


    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    private void actualizar2() {		
		Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
        .ifPresentOrElse(fila -> {
        	
        	  String gasto = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
              String tipo = (String) modelo.getValueAt(tabla.getSelectedRow(), 3);
              Double costo = (Double) modelo.getValueAt(tabla.getSelectedRow(), 4);
              Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
              System.out.println("ID to update: " + id);
           
            
              
              this.gastoController.actualizar(gasto, tipo, costo, id);
			JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
		}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
		
	}
    
    private void actualizar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
        .ifPresentOrElse(fila -> {
            Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

            // Actualizar los campos con los valores de la interfaz
            textoNombreGasto.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 2));
            textoTipo.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 3));

            // Obtener el valor de textoCosto
            String textoCostoString = textoCosto.getText();

            // Verificar si la cadena está vacía o nula antes de intentar la conversión
            double costo = textoCostoString.isEmpty() ? 0 : Double.parseDouble(textoCostoString);

            // Luego, puedes imprimir los valores para verificar
            System.out.println("ID to update: " + id);
            System.out.println("Texto Nombre Gasto: " + textoNombreGasto.getText());
            System.out.println("Texto Tipo: " + textoTipo.getText());
            System.out.println("Texto Costo: " + costo);

            // Finalmente, llamar al método actualizar del controlador
            this.gastoController.actualizar(textoNombreGasto.getText(), textoTipo.getText(), costo, id);

            JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
        }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));

    }
 
 
    
    private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
                    this.gastoController.eliminar(id);	
            
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, " Item eliminado con éxito!");
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
    
     
   private List<Gastos> ListarGasto() {
		return this.gastoController.listar();
   }
     
   private void cargarTabla() {			       
	    //Llenar Tabla
		List<Gastos> gasto = (List<Gastos>) ListarGasto();
		try {
			for (Gastos gastos : gasto) {
				modelo.addRow(new Object[] { gastos.getIdGasto(), gastos.getPeriodoGasto(), gastos.getNombreGasto(),
						gastos.getTipo(),gastos.getCosto()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
  
   private void guardar() throws SQLException {
	    // No declares una nueva variable local aquí
	    String mesSeleccionado = (String) BoxPeriodo.getSelectedItem();
	    Double costo = Double.parseDouble(textoCosto.getText());

	    Gastos gasto = new Gastos(mesSeleccionado, textoNombreGasto.getText(), textoTipo.getText(), costo);
	    this.gastoController.guardar(gasto);

	    JOptionPane.showMessageDialog(this, "Registrado con éxito!");

	    this.limpiarFormulario();
	}
    
   
    private void limpiarFormulario() {
    	this.BoxPeriodo.setAction(null);;
        this.textoNombreGasto.setText("");
        this.textoTipo.setText("");
        this.textoCosto.setText("");
    }
}
