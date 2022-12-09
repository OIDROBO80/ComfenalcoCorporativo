package com.corporativos_smartfit.util;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class Util {
	private static final Logger LOG = Logger.getLogger(Util.class);
	private static final String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String getStaticImagesDir() {
		String pathDir;
		String so = System.getProperty("os.name");
		if (so.toLowerCase().contains("windows")) {
			pathDir = System.getProperty("user.dir") + "\\static\\images\\";
		} else {
			pathDir = System.getProperty("user.dir") + "/static/images/";
		}
		
		return pathDir;
	}
	
	public static String getStaticDir() {
		String pathDir;
		String so = System.getProperty("os.name");
		if (so.toLowerCase().contains("windows")) {
			pathDir = System.getProperty("user.dir") + "\\static\\";
		} else {
			pathDir = System.getProperty("user.dir") + "/static/";
		}
		
		return pathDir;
	}


	public static String guardarBase64Archivo(String base64String) {
		// Obtener la ruta del directorio de imagenes
		String so = System.getProperty("os.name");
		String pathDir = getStaticImagesDir();

		// Crear el directorio
		File dir = new File(pathDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String[] strings = base64String.split(",");
		String extension;
		switch (strings[0]) {
		case "data:image/jpeg;base64":
			extension = "jpeg";
			break;
		case "data:image/png;base64":
			extension = "png";
			break;
		default:
			extension = "jpg";
			break;
		}
		// Current time
		Date date = new Date();
		long timeMilli = date.getTime();

		String nameImage = timeMilli + randomAlphaNumeric(10) + "." + extension;

		// convert base64 string to binary data
		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
		String pathImage = pathDir + nameImage;
		File file = new File(pathImage);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			outputStream.write(data);
			LOG.info("Imagen almacenada en: " + pathImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameImage;
	}


	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}


	public static byte[] extractBytes(String pathImage) throws IOException {
		// open image
		File imgPath = new File(pathImage);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}
	

	public static Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}
