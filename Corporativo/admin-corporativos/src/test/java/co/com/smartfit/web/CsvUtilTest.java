package co.com.smartfit.web;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import co.com.smartfit.web.util.CsvUtil;

/**
 * Test de lectura de CSV
 * @author omar.cardona
 *
 */
public class CsvUtilTest {

    static CsvUtil csvUtil;
    static String csvEmpleados;
    static String csvCodigos;
    static String rutaArchivoSalida;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        csvUtil = new CsvUtil();
        ClassLoader classLoader = new CsvUtilTest().getClass().getClassLoader();
        csvEmpleados = classLoader.getResource("EmpleadosSample.csv").getPath();
        csvCodigos = classLoader.getResource("CodigosSample.csv").getPath();
        rutaArchivoSalida = classLoader.getResource("").getPath() + "Generate.csv";
    }

    /**
     * Leer Afiliados a partir de una ruta de CSV
     */
    @Test
    public void obtenerAfiliadosCsvTest() {
        System.out.println("\n"+"obtenerAfiliadosCsvTest()");
        long timeInicio = new Date().getTime();
        
        List<String[]> registros = csvUtil.obtenerFilasCsv(csvEmpleados, false);
        
        String[] fila1 = registros.get(0);
        String[] fila2 = registros.get(1);
        String[] fila3 = registros.get(2);

        // Imprimimos arreglo por ciclo
        System.out.println(" RESULT ARREGLO:");
        for (int i = 0; i < registros.size(); i++) {
            String[] reg = registros.get(i);
            System.out.println("  >Fila " + (i + 1) + " de " + registros.size() + ": [0]=" + reg[0] + " [1]=" + reg[1]  + " [2]=" + reg[2]  + " [3]=" + reg[3]);
        }
        // comprobando resultados para test
        boolean resultado = true;
        resultado = (fila1[0].equals("Tipo Documento") && fila1[1].equals("Documento") && fila1[2].equals("Nombre") && fila1[3].equals("Correo"));
        resultado = (resultado == false) ? false : (fila2[0].equals("CC") && fila2[1].equals("1053781290") && fila2[2].equals("Omar Cardona") && fila2[3].equals("omar.cardona@pragma.com.co"));
        resultado = (resultado == false) ? false : (fila3[0].equals("CC") && fila3[1].equals("1053809190") && fila3[2].equals("Augusto Suárez") && fila3[3].equals("augusto.suarez@pragma.com.co"));

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }
    
    /**
     * Leer Códigos de Descuento a partir de una ruta de CSV
     */
    @Test
    public void obtenerCodigosCsvTest() {
        System.out.println("\n"+"obtenerCodigosCsvTest()");
        long timeInicio = new Date().getTime();
        
        List<String[]> registros = csvUtil.obtenerFilasCsv(csvCodigos, false);
        
        String[] fila1 = registros.get(0);
        String[] fila2 = registros.get(1);
        String[] fila3 = registros.get(2);

        // Imprimimos arreglo
        System.out.println(" RESULT ARREGLO:");
        for (int i = 0; i < registros.size(); i++) {
            String[] reg = registros.get(i);
            System.out.println("  >Fila " + (i + 1) + " de " + registros.size() + ": [0]=" + reg[0]);
        }
        // comprobando resultados para test
        boolean resultado = true;
        resultado = (fila1[0].equals("Codigo"));
        resultado = (resultado == false) ? false : (fila2[0].equals("ABC001"));
        resultado = (resultado == false) ? false : (fila3[0].equals("ABC002"));

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }
    
    /**
     * Leer Afiliados a partir de base 64
     */
    @Test
    public void obtenerAfiliadosCsvBase64Test() {
        System.out.println("\n"+"obtenerAfiliadosCsvBase64Test()");
        long timeInicio = new Date().getTime();

        String archivoBase64 = "VGlwbyBEb2N1bWVudG8sRG9jdW1lbnRvLE5vbWJyZSxDb3JyZW8NCkNDLDEwNTM3ODEyOTAsT21hciBDYXJkb25hLG9tYXIuY2FyZG9uYUBwcmFnbWEuY29tLmNvDQpDQywxMDUzODA5MTkwLEF1Z3VzdG8gU3XDoXJleixhdWd1c3RvLnN1YXJlekBwcmFnbWEuY29tLmNv";
        
        List<String[]> registros = csvUtil.obtenerFilasCsvBase64(archivoBase64, false);
        String[] fila1 = registros.get(0);
        String[] fila2 = registros.get(1);
        String[] fila3 = registros.get(2);
        
        // Imprimimos arreglo por ciclo
        System.out.println(" RESULT ARREGLO:");
        for (int i = 0; i < registros.size(); i++) {
            String[] reg = registros.get(i);
            System.out.println("  >Fila " + (i + 1) + " de " + registros.size() + ": [0]=" + reg[0] + " [1]=" + reg[1]  + " [2]=" + reg[2]  + " [3]=" + reg[3]);
        }
        // comprobando resultados para test
        boolean resultado = true;
        resultado = (fila1[0].equals("Tipo Documento") && fila1[1].equals("Documento") && fila1[2].equals("Nombre") && fila1[3].equals("Correo"));
        resultado = (resultado == false) ? false : (fila2[0].equals("CC") && fila2[1].equals("1053781290") && fila2[2].equals("Omar Cardona") && fila2[3].equals("omar.cardona@pragma.com.co"));
        resultado = (resultado == false) ? false : (fila3[0].equals("CC") && fila3[1].equals("1053809190") && fila3[2].equals("Augusto Suárez") && fila3[3].equals("augusto.suarez@pragma.com.co"));

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }

    /**
     * Leer Códigos de Descuento a partir de base 64
     */
    @Test
    public void obtenerCodigosCsvBase64Test() {
        System.out.println("\n"+"obtenerCodigosCsvBase64Test()");
        long timeInicio = new Date().getTime();

        String archivoBase64 = "IkNvZGlnbyIKIkFCQzAwMSIKIkFCQzAwMiIK";
        
        List<String[]> registros = csvUtil.obtenerFilasCsvBase64(archivoBase64, false);
        String[] fila1 = registros.get(0);
        String[] fila2 = registros.get(1);
        String[] fila3 = registros.get(2);
        
        // Imprimimos arreglo
        System.out.println(" RESULT ARREGLO:");
        for (int i = 0; i < registros.size(); i++) {
            String[] reg = registros.get(i);
            System.out.println("  >Fila " + (i + 1) + " de " + registros.size() + ": [0]=" + reg[0]);
        }
        // comprobando resultados para test
        boolean resultado = true;
        resultado = (fila1[0].equals("Codigo"));
        resultado = (resultado == false) ? false : (fila2[0].equals("ABC001"));
        resultado = (resultado == false) ? false : (fila3[0].equals("ABC002"));

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }

    /**
     * Generar CSV
     */
    @Test
    public void generarCsvTest() {
        System.out.println("\n"+"generarCsvTest()" + rutaArchivoSalida);
        long timeInicio = new Date().getTime();

        ArrayList<String[]> filas = new ArrayList<>();
        String[] fila1 = new String[2];
        fila1[0] = "Benjamin Picaso";
        fila1[1] = "benja@pra.com";
        filas.add(fila1);
        String[] fila2 = new String[2];
        fila2[0] = "Felipe Manson";
        fila2[1] = "phllip@j.fray";
        filas.add(fila2);
        boolean resultado = csvUtil.generarCsv(rutaArchivoSalida, filas);

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        boolean esperado = true;
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }

    

    /**
     * Convertir un CSV en string Base 64
     */
    @Test
    public void generarCsvBase64Test() {
        System.out.println("\n"+"generarCsvBase64Test()");
        long timeInicio = new Date().getTime();
        
        List<String[]> registros = csvUtil.obtenerFilasCsv(csvCodigos, false);
        
        String[] fila1 = registros.get(0);
        String[] fila2 = registros.get(1);
        String[] fila3 = registros.get(2);

        ArrayList<String[]> filas = new ArrayList<>();
        filas.add(fila1);
        filas.add(fila2);
        filas.add(fila3);
        
        String resultado;
        try {
            resultado = csvUtil.generarCsvBase64(filas);
        } catch (IOException e) {
            resultado = null;
        }

        long timeFin = new Date().getTime();
        System.out.println("\tRESULT: " + resultado);
        System.out.println("\tTIEMPO: " + (timeFin - timeInicio) + " milliseconds");
        String esperado = "IkNvZGlnbyIKIkFCQzAwMSIKIkFCQzAwMiIK";
        System.out.println("\tESPERADO: " + esperado);
        assertEquals(esperado, resultado);
    }
}
