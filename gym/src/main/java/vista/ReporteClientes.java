package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.ReporteController;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class ReporteClientes extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    //********************************
    private JPanel contentPane;
    private JLabel labelPeriodo;
	

	private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
	

    //private ClienteController clienteController;
    private ReporteController reporteControler;
    private JTextField textTotal;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteClientes frame = new ReporteClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReporteClientes() {
    	super("Lista Clientes");
    	this.reporteControler = new ReporteController();
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenido(container);
    	
    	JLabel lblNewLabel = new JLabel("Reporte de Clientes");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
    	lblNewLabel.setBounds(309, 21, 218, 36);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblPeriodo = new JLabel("Periodo");
    	lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblPeriodo.setBounds(170, 108, 100, 22);
    	getContentPane().add(lblPeriodo);
    	
    	JComboBox BoxPeriodo = new JComboBox();
    	BoxPeriodo.setModel(new DefaultComboBoxModel(new String[] {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
    	BoxPeriodo.setBounds(429, 105, 148, 22);
    	getContentPane().add(BoxPeriodo);
    	
    	textTotal = new JTextField();
    	textTotal.setEditable(false);
    	textTotal.setBounds(455, 497, 86, 20);
    	getContentPane().add(textTotal);
    	textTotal.setColumns(10);
    	
    	 // Crear un botón u otro evento que desencadene la acción de la suma
        JButton btnCalcularSuma = new JButton("Calcular Suma");
        btnCalcularSuma.setBounds(300, 497, 120, 25);
        getContentPane().add(btnCalcularSuma);

        // Agregar un ActionListener al botón para manejar la acción
        btnCalcularSuma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Llamar a la función realizarSuma y mostrar el resultado en textTotal
                double resultadoSuma = 0;
				try {
					resultadoSuma = reporteControler.realizarSuma();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                textTotal.setText(String.valueOf(resultadoSuma));
            }
        });
    	
    }
    
    
    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Fecha Alta");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Precio");
    
        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        container.add(tabla);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void cargarTabla() {
        List<Map<String, String>> clientes = new ArrayList<Map<String, String>>();

        try {
        	clientes = this.reporteControler.reporteClientes();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clientes.forEach(producto -> modelo.addRow(
                new Object[] {
                		producto.get("fechaAlta"),
                        producto.get("nombre"),
                        producto.get("apellido"),
                        producto.get("precio")}));
    }

}