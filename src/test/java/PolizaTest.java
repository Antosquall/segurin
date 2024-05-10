import com.mycompany.model.Poliza;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class PolizaTest {

    @Test
    public void testSetAndGetIdPoliza() {
        Poliza poliza = new Poliza();
        poliza.setIdPoliza(101);
        assertEquals(101, poliza.getIdPoliza(), "El ID de la póliza debe ser 101");
    }

    @Test
    public void testSetAndGetIdCliente() {
        Poliza poliza = new Poliza();
        poliza.setID_Cliente(202);
        assertEquals(202, poliza.getID_Cliente(), "El ID del cliente de la póliza debe ser 202");
    }

    @Test
    public void testSetAndGetNumeroPoliza() {
        Poliza poliza = new Poliza();
        poliza.setNumeroPoliza("POL12345");
        assertEquals("POL12345", poliza.getNumeroPoliza(), "El número de la póliza debe ser POL12345");
    }

}
