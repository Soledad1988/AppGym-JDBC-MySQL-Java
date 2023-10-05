package vista;

import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;


public class ReporteClientes extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;

    private ClienteController clienteController;

    public ReporteClientes(ListaClientesFrame listaClientesFrame) throws SQLException {
        super("Reporte");

        this.clienteController = new ClienteController();

        Container container = getContentPane();
        setLayout(null);

        tablaReporte = new JTable();
        tablaReporte.setBounds(0, 0, 600, 400);
        container.add(tablaReporte);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");

        cargaReporte();

        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(listaClientesFrame);
    }

    private void cargaReporte() throws SQLException {
        var contenido = clienteController.cargaReporte();
        
        contenido.forEach(fila -> modelo
                .addRow(new Object[] {}));
    }

}