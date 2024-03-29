package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.GastosController;

import gym.modelo.Gastos;


public class AltaGastosFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombreGasto, labelDescripcion, labelCosto;
    private JTextField textoNombreGasto, textoDescripcion, textoCosto;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private GastosController gastoController;
    public static JDateChooser textFechaGasto;
    
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
        
        /*----------------------------------*/
        
        textFechaGasto = new JDateChooser();
        textFechaGasto.getCalendarButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        textFechaGasto.getCalendarButton().setBackground(SystemColor.textHighlight);
        textFechaGasto.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
        textFechaGasto.setBounds(345, 52, 265, 20);
        textFechaGasto.getCalendarButton().setBounds(268, 0, 21, 33);
        textFechaGasto.setBackground(Color.WHITE);
        textFechaGasto.setBorder(new LineBorder(SystemColor.window));
        textFechaGasto.setDateFormatString("yyyy-MM-dd");
        textFechaGasto.setFont(new Font("Roboto", Font.PLAIN, 18));
		//panel.add(textFechaIngreso);
        getContentPane().add(textFechaGasto);
        
        JLabel lblTitulo = new JLabel("Gastos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(325, 0, 213, 30);
        getContentPane().add(lblTitulo);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Perido Gasto");
        modelo.addColumn("Gasto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Costo");

        tabla.setModel(modelo); 
        
        cargarTabla();
        
        // Ocultar la columna de ID
        tabla.removeColumn(tabla.getColumnModel().getColumn(0));

        tabla.setBounds(10, 286, 760, 143);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        
        botonEliminar.setBounds(267, 491, 96, 20);
        botonModificar.setBounds(377, 491, 94, 20);
        

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        
        JScrollPane scrollPane = new JScrollPane(tabla); // Añadir la tabla a un JScrollPane
        scrollPane.setBounds(10, 269, 760, 215);
        container.add(scrollPane); // Agregar el JScrollPane al contenedor

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombreGasto = new JLabel("Gasto");
        labelNombreGasto.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelDescripcion = new JLabel("Descripcion");
        labelDescripcion.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelCosto = new JLabel("Costo");
        labelCosto.setFont(new Font("Tahoma", Font.BOLD, 12));

        labelNombreGasto.setBounds(35, 102, 240, 15);
        labelDescripcion.setBounds(35, 148, 240, 15);
        labelCosto.setBounds(35, 194, 240, 15);
        
        labelNombreGasto.setForeground(Color.BLACK);
        labelDescripcion.setForeground(Color.BLACK);
        labelCosto.setForeground(Color.BLACK);

        textoNombreGasto = new JTextField();
        textoDescripcion = new JTextField();
        textoCosto = new JTextField();

        // TODO
        
        textoNombreGasto.setBounds(345, 100, 265, 20);
        textoDescripcion.setBounds(345, 148, 265, 20);
        textoCosto.setBounds(345, 197, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(277, 238, 92, 20);
        botonLimpiar.setBounds(379, 238, 92, 20);

        container.add(labelNombreGasto);
        container.add(labelDescripcion);
        container.add(labelCosto);
        container.add(textoNombreGasto);
        container.add(textoDescripcion);
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

    }

    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    private void actualizar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            Integer id = Integer.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString());

            // Obtener los valores actuales de la fila seleccionada
            String nombreGastoActual = (String) modelo.getValueAt(filaSeleccionada, 2);
            String descripcionActual = (String) modelo.getValueAt(filaSeleccionada, 3);

            // Mostrar un cuadro de diálogo o utilizar algún otro método para obtener el nuevo valor de costo
            String nuevoCostoString = JOptionPane.showInputDialog(this, "Ingrese el nuevo valor de costo:");

            try {
                // Convertir a double
                double nuevoCosto = Double.parseDouble(nuevoCostoString);

                // Luego, puedes imprimir los valores para verificar
                System.out.println("ID to update: " + id);
                System.out.println("Texto Gasto: " + nombreGastoActual);
                System.out.println("Texto Descripcion: " + descripcionActual);
                System.out.println("Nuevo Costo: " + nuevoCosto);

                // Finalmente, llamar al método actualizar del controlador
                this.gastoController.actualizar(nombreGastoActual, descripcionActual, nuevoCosto, id);

                JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor de costo válido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elije un registro");
        }
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
		List<Gastos> gasto = (List<Gastos>) ListarGasto();
		try {
			for (Gastos gastos : gasto) {
				modelo.addRow(new Object[] { 
						gastos.getIdGasto(), 
						gastos.getFechaGasto(), 
						gastos.getNombreGasto(),
						gastos.getDescripcion(),
						gastos.getCosto()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
  
   private void guardar() throws SQLException {
	    String fechaEgreso = ((JTextField)textFechaGasto.getDateEditor().getUiComponent()).getText();
	    Double costo = Double.parseDouble(textoCosto.getText());

	    Gastos gasto = new Gastos(
	    		java.sql.Date.valueOf(fechaEgreso), 
	    		textoNombreGasto.getText(), 
	    		textoDescripcion.getText(), 
	    		costo);
	    
	    this.gastoController.guardar(gasto);

	    JOptionPane.showMessageDialog(this, "Registrado con éxito!");

	    this.limpiarFormulario();
	}
    
   
    @SuppressWarnings("static-access")
	private void limpiarFormulario() {
    	this.textFechaGasto.setDate(null);
        this.textoNombreGasto.setText("");
        this.textoDescripcion.setText("");
        this.textoCosto.setText("");
    }
}
