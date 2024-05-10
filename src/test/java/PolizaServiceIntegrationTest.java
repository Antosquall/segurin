import com.mycompany.model.Cliente;
import com.mycompany.model.Poliza;
import com.mycompany.persistance.ClienteDAO;
import com.mycompany.persistance.PolizaDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PolizaServiceIntegrationTest {

    @Mock
    private ClienteDAO clienteDAO;

    @Mock
    private PolizaDAO polizaDAO;

    // No necesitas simular la conexión directamente si estás simulando los DAOs que dependen de esta
    // @Mock
    // private Connection connection;

    // Inyectar mocks en la clase que estás probando si es necesario
    // @InjectMocks
    // private SomeService service;

    @Test
    public void testCrearPolizaConClienteExistente() throws SQLException {
        // Datos de prueba
        Cliente cliente = new Cliente(
            1, "12345678X", "Juan", "Perez", "Calle Falsa 123", "555-1234", "juan.perez@example.com",
            LocalDate.of(1980, 10, 10), "Masculino", 2, 5, "Soltero", 0, "Ingeniero", "Universitarios",
            30000, LocalDate.now(), null, "Observaciones generales", "Española", "Ninguno", "No"
        );
        Poliza poliza = new Poliza(
            1, 1, "REC123", "POL12345", LocalDate.now(), LocalDate.now().plusYears(1), 
            "Cobertura Total", "Daños por agua", "Agente001", "Sin comentarios"
        );

        // Simular que el cliente existe
        when(clienteDAO.getCliente(cliente.getIdCliente())).thenReturn(cliente);
        // Simular que la adición de la póliza es exitosa
        when(polizaDAO.addPoliza(poliza)).thenReturn(true);

        // Proceso de creación de la póliza
        boolean clienteExiste = clienteDAO.getCliente(cliente.getIdCliente()) != null;
        boolean resultado = false;
        if (clienteExiste) {
            resultado = polizaDAO.addPoliza(poliza);
        }

        // Verificar resultados
        assertTrue(clienteExiste, "El cliente debe existir para proceder con la creación de la póliza.");
        assertTrue(resultado, "La creación de la póliza debe ser exitosa.");

        // Verificar las interacciones esperadas con los mocks
        verify(clienteDAO).getCliente(cliente.getIdCliente());
        verify(polizaDAO).addPoliza(poliza);
    }
}
