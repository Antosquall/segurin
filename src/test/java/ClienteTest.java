import com.mycompany.model.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClienteTest {

    @Test
    public void testSetAndGetIdCliente() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(123);
        assertEquals(123, cliente.getIdCliente(), "El ID del cliente debe ser 123");
    }

    @Test
    public void testSetAndGetDNI() {
        Cliente cliente = new Cliente();
        cliente.setDNI("12345678A");
        assertEquals("12345678A", cliente.getDNI(), "El DNI del cliente debe ser 12345678A");
    }

    @Test
    public void testSetAndGetNombre() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        assertEquals("Juan", cliente.getNombre(), "El nombre del cliente debe ser Juan");
    }

    @Test
    public void testSetAndGetApellido() {
        Cliente cliente = new Cliente();
        cliente.setApellido("Pérez");
        assertEquals("Pérez", cliente.getApellido(), "El apellido del cliente debe ser Pérez");
    }


}
