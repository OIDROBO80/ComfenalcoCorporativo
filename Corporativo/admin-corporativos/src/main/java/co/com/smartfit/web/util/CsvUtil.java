/**
 * 
 */
package co.com.smartfit.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * Clase encargada de leer archivos en formato CVS y convertirlos a un arreglo de Strings.
 * 
 * @author alejandro.areiza
 */
public class CsvUtil {

    private static final Logger LOG = Logger.getLogger(CsvUtil.class);

    /*
     * ruta designada para una rchivo temporal, necesario para generar B64
     */
  
    //Servidores
    private String RUTA_TEMPORAL_ARCHIVO = "/tmp/GenerateTmp64.csv";
    
    public CsvUtil() {
    	String so = System.getProperty("os.name"); 
    	if(so.toLowerCase().contains("windows")) {
    		RUTA_TEMPORAL_ARCHIVO = System.getProperty("java.io.tmpdir") + "GenerateTmp64.csv";
    	}
    }

    /**
     * Metodo que permite obtener una lista de filas a partir de un archivo CVS ubicado localmente
     * 
     * @param csvEmpleados
     *            String que indica donde se encuentra fisicamente el archivo (path)
     * @return List<String[]>, Arreglo de filas con sus respectivas columnas separadas en arreglo de String
     */
    public List<String[]> obtenerFilasCsv(String rutaArchivo, boolean conCabecera) {
        List<String[]> registros = new ArrayList<>();
        FileReader fileReader = null;
        try {
            // leemos el archivo
            fileReader = new FileReader(rutaArchivo);
            registros = obtenerFilasCsv(fileReader, conCabecera);
        } catch (FileNotFoundException e) {
            registros = new ArrayList<>();
            // throw new MyException("");
        } catch (Exception e) {
            registros = new ArrayList<>();
            // throw new MyException("");
        }
        return registros;
    }

    /**
     * Metodo que permite obtener una lista de filas a partir de un archivo CVS en base 64
     * 
     * @param archivoBase64
     *            String que representa el archivo codificado en base64
     * @return List<String[]>, Arreglo de filas con sus respectivas columnas separadas en arreglo de String
     */
    public List<String[]> obtenerFilasCsvBase64(String archivoBase64, boolean conCabecera) throws IllegalArgumentException {
        List<String[]> registros = new ArrayList<>();
        LOG.info("Obteniendo los registros del csv");
        // convertimos el base 64 a un reader entendible para el API
        try {
            byte[] fileByte = Base64.getDecoder().decode(archivoBase64);
            Reader file = new StringReader(new String(fileByte));
            // obtenemos las filas
            registros = obtenerFilasCsv(file, conCabecera);
        } catch (IllegalArgumentException e) {
            registros = new ArrayList<>();
            // throw new MyException("");
            throw new IllegalArgumentException("Problema en Util al procesar el CSV en Base64 " + e.getMessage(), e);
        } catch (Exception e) {
            registros = new ArrayList<>();
            // throw new MyException("");
        }
        return registros;
    }

    /**
     * Metodo que permite obetener las filas de un CVS desde un fileReader
     * 
     * @param fileReader
     *            Reader del cual se tomará el archivo CSV
     * @param conCabecera
     *            boolean que indica si la primera fila es una cabezera, ´por lo que será ignorada
     * @return List<String[]>, Arreglo de filas con sus respectivas columnas separadas en arreglo de String
     */
    private List<String[]> obtenerFilasCsv(Reader fileReader, boolean conCabecera) {
        ArrayList<String[]> registros = new ArrayList<>();
        try {
            // Clase para conversion de CSV files
            CSVReader reader = new CSVReader(fileReader);
            // obtenemos cada linea del archivo y la transformamos
            String[] line;
            if (conCabecera) {
                // leemos e ignoramos la prima linea
                reader.readNext();
            }
            while (null != (line = reader.readNext())) {
                registros.add(line);
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            registros = new ArrayList<>();
            // throw new MyException("");
        } catch (Exception e) {
            registros = new ArrayList<>();
            // throw new MyException("");
        }
        return registros;
    }

    /**
     * Metodo que permite generar un CSV a partir de una lista de filas
     * 
     * @param rutaSalida
     *            ruta en la que se almacenará el archivo generado
     * @param filas
     *            List que contiene los datos de cada fila
     * @return boolean, indica si se generó correctamente el archivo
     */
    public boolean generarCsv(String rutaSalida, List<String[]> filas) {
        FileWriter fileWriter = null;
        CSVWriter writer = null;
        // creamos el archivo de salida
        try {
            fileWriter = new FileWriter(rutaSalida);
            writer = new CSVWriter(fileWriter);
            writer.writeAll(filas);

            return true;
        } catch (IOException e) {
            LOG.error("IOException generarCsv", e);
        } catch (Exception e) {
            LOG.error("Exception generarCsv", e);
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Metodo que permite generar un CSV en base 64 a partir de una lista de filas
     * 
     * @param filas
     *            List que contiene los datos de cada fila
     * @return String, archivo en Base64
     * @throws IOException
     */
    public String generarCsvBase64(List<String[]> filas) throws IOException {
        String base64 = null;
        String rutaTemporal = RUTA_TEMPORAL_ARCHIVO;

        if (generarCsv(rutaTemporal, filas)) {
            // codificamos ela rchivo en String
            File file = null;
            FileInputStream inputStream = null;
            try {
                file = new File(rutaTemporal);
                inputStream = new FileInputStream(file);
                byte bytes[] = new byte[(int) file.length()];
                inputStream.read(bytes);
                base64 = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException e) {
                LOG.error("IOException generarCsvBase64", e);
                throw e;
            } catch (Exception e) {
                LOG.error("Exception generarCsvBase64", e);
                throw e;
            } finally {
                if (null != inputStream) {
                    inputStream.close();
                }
            }
        }
        return base64;
    }
}
