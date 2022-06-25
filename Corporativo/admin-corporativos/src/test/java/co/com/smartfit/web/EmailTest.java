package co.com.smartfit.web;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.smartfit.web.shipping.EmailShipping;

public class EmailTest {

	static final Logger logger = LoggerFactory.getLogger(EmailTest.class);

	@Test
	public void enviarEmailVipTest() {
		System.out.println("\n"+"enviarEmailEmpleadoTest()");
		long timeInicio = new Date().getTime();
		
		boolean resultado = EmailShipping.enviarEmailMembresiaVip("omar.cardona@pragma.com.co", "Omar Cardona", "ABC001",
				"https://www.smartfit.com.co/assets/v2/shared/logos/logo-slogan-es-co-3d31dcfb16ddb87f6e90b22ae2aeb780912bbf621a1af3d9a1feb83a624675de.png",
				"Hola!!!<br>Texto personalizado email");
		
		long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
	}
}
