package vista;

import java.awt.Container;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ClienteController;
import gym.modelo.Cliente;
import javax.swing.JScrollPane;


public class TurnosFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;
    

    public TurnosFrame() throws SQLException {
        super("Reporte de Turnos");

        this.clienteController = new ClienteController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarTablaDeContenido(container);

        setSize(554, 497);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = new DefaultTableModel();
        modelo.addColumn("Horario");
        modelo.addColumn("Cantidad de Personas Inscriptas");

        tabla.setModel(modelo);

        cargarTabla();

        tabla.setBounds(10, 30, 760, 400);

        container.add(tabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 30, 520, 400);
        container.add(scrollPane);
    }

    private void cargarTabla() {
        List<String> horarios = obtenerHorarios();
        for (String horario : horarios) {
            int cantidad = clienteController.contarPersonasPorHorario(horario);
            modelo.addRow(new Object[]{horario, cantidad});
        }
    }

    private List<String> obtenerHorarios() {
        // Obtener la lista de horarios únicos
        return clienteController.listar().stream()
                .map(Cliente::getHorario)
                .distinct()
                .toList();
    }
}
