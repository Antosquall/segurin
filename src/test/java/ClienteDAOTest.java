
import com.mycompany.model.Cliente;
import com.mycompany.persistance.ClienteDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClienteDAOTest {

    @Test
    public void testGetAllClientes() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM clientes")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("ID_Cliente")).thenReturn(1);
        when(mockResultSet.getString("Nombre")).thenReturn("Juan");

        ClienteDAO clienteDAO = new ClienteDAO(mockConnection);
        List<Cliente> clientes = clienteDAO.getAllClientes();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
    }

    @Test
    public void testGetCliente() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("ID_Cliente")).thenReturn(1);
        when(mockResultSet.getString("Nombre")).thenReturn("Juan");

        ClienteDAO clienteDAO = new ClienteDAO(mockConnection);
        Cliente cliente = clienteDAO.getCliente(1);

        assertNotNull(cliente);
        assertEquals(1, cliente.getIdCliente());
        assertEquals("Juan", cliente.getNombre());
    }

   
    @Test
public void testDeleteCliente() throws SQLException {
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simula que una fila fue eliminada

    ClienteDAO clienteDAO = new ClienteDAO(mockConnection);
    boolean result = clienteDAO.deleteCLiente(1);

    assertTrue(result);
    verify(mockPreparedStatement).executeUpdate();
}


}
