package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
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


public class ReporteGastos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;
    
    //********************************
    private JPanel contentPane;
    private JLabel labelPeriodo;
	

	private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
	

    //private ClienteController clienteController;
    private ReporteController reporteControler;
    private JTextField textTotal;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteGastos frame = new ReporteGastos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReporteGastos() {
    	super("Lista Clientes");
    	this.reporteControler = new ReporteController();
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenido(container);
    	
    	JLabel lblNewLabel = new JLabel("Reporte de Gastos");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
    	lblNewLabel.setBounds(309, 21, 218, 36);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblPeriodo = new JLabel("Periodo");
    	lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblPeriodo.setBounds(102, 128, 100, 22);
    	getContentPane().add(lblPeriodo);
    	
    	JComboBox BoxPeriodo = new JComboBox();
    	BoxPeriodo.setModel(new DefaultComboBoxModel(new String[] {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
    	BoxPeriodo.setBounds(207, 131, 148, 22);
    	getContentPane().add(BoxPeriodo);
    	
    	JLabel total = new JLabel("Total");
    	total.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	total.setBounds(339, 494, 65, 20);
    	getContentPane().add(total);
    	
    	textTotal = new JTextField();
    	textTotal.setEditable(false);
    	textTotal.setBounds(455, 497, 86, 20);
    	getContentPane().add(textTotal);
    	textTotal.setColumns(10);
    	
    	JLabel lblNewLabel_1 = new JLabel("Luz");
    	lblNewLabel_1.setBounds(118, 174, 49, 14);
    	getContentPane().add(lblNewLabel_1);
    	
    	textField = new JTextField();
    	textField.setBounds(212, 175, 96, 20);
    	getContentPane().add(textField);
    	textField.setColumns(10);
    	
    	JLabel lblNewLabel_1_1 = new JLabel("Alquiler");
    	lblNewLabel_1_1.setBounds(114, 217, 49, 14);
    	getContentPane().add(lblNewLabel_1_1);
    	
    	textField_1 = new JTextField();
    	textField_1.setColumns(10);
    	textField_1.setBounds(209, 209, 96, 20);
    	getContentPane().add(textField_1);
    	
    	JLabel lblNewLabel_1_1_1 = new JLabel("Agua");
    	lblNewLabel_1_1_1.setBounds(116, 261, 49, 14);
    	getContentPane().add(lblNewLabel_1_1_1);
    	
    	textField_2 = new JTextField();
    	textField_2.setColumns(10);
    	textField_2.setBounds(204, 254, 96, 20);
    	getContentPane().add(textField_2);
    	
    	JLabel lblNewLabel_1_1_1_1 = new JLabel("Sueldos");
    	lblNewLabel_1_1_1_1.setBounds(113, 298, 49, 14);
    	getContentPane().add(lblNewLabel_1_1_1_1);
    	
    	textField_3 = new JTextField();
    	textField_3.setColumns(10);
    	textField_3.setBounds(208, 294, 96, 20);
    	getContentPane().add(textField_3);
    	
    	JLabel lblTotalIngresos = new JLabel("Total Ingresos");
    	lblTotalIngresos.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblTotalIngresos.setBounds(91, 401, 100, 22);
    	getContentPane().add(lblTotalIngresos);
    	
    	textField_4 = new JTextField();
    	textField_4.setColumns(10);
    	textField_4.setBounds(205, 399, 96, 20);
    	getContentPane().add(textField_4);
    	
    }
    
    
    private void configurarTablaDeContenido(Container container) {
        modelo.addColumn("Fecha Alta");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Precio");
    
        cargarTabla();

        botonEliminar = new JButton("Deudores");
        botonModificar = new JButton("Modificar");
        botonReporte = new JButton("Ver Reporte");
        botonEliminar.setBounds(10, 500, 80, 20);
        botonModificar.setBounds(100, 500, 80, 20);
        botonReporte.setBounds(190, 500, 80, 20);
        /*container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonReporte);*/

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }



    private void cargarTabla() {
        List<Map<String, String>> clientes = new ArrayList<Map<String, String>>();

        try {
        	clientes = this.reporteControler.reporte();
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