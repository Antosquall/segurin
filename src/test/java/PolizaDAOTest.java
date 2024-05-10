import com.mycompany.model.Poliza;
import com.mycompany.persistance.PolizaDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PolizaDAOTest {

    @Test
    public void testAddPoliza() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simula que una fila fue afectada

        Poliza poliza = new Poliza(
            1, // ID_Poliza
            10, // ID_Cliente
            "REC123", // ID_Recibo
            "POL12345", // Numero_Poliza
            LocalDate.now(), // Fecha_Emision
            LocalDate.now().plusYears(1), // Fecha_Vencimiento
            "Cobertura Total", // Tipo_Cobertura
            "Daños por agua", // Cobertura_Adicional
            "Agente001", // Comercial
            "Sin comentarios" // Comentarios
        );

        PolizaDAO polizaDAO = new PolizaDAO(mockConnection);
        boolean result = polizaDAO.addPoliza(poliza);

        assertTrue(result, "La adición de la póliza debe ser exitosa.");
        verify(mockPreparedStatement).setInt(1, poliza.getIdPoliza());
        verify(mockPreparedStatement).setInt(2, poliza.getID_Cliente());
        verify(mockPreparedStatement).setString(3, poliza.getIdRecibo());
        verify(mockPreparedStatement).setString(4, poliza.getNumeroPoliza());
        verify(mockPreparedStatement).setDate(5, java.sql.Date.valueOf(poliza.getFechaEmision()));
        verify(mockPreparedStatement).setDate(6, java.sql.Date.valueOf(poliza.getFechaVencimiento()));
        verify(mockPreparedStatement).setString(7, poliza.getTipoCobertura());
        verify(mockPreparedStatement).setString(8, poliza.getCoberturaAdicional());
        verify(mockPreparedStatement).setString(9, poliza.getComercial());
        verify(mockPreparedStatement).setString(10, poliza.getComentarios());
    }

}
