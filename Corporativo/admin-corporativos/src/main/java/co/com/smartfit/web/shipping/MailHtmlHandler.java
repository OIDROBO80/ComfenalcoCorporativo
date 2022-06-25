package co.com.smartfit.web.shipping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailHtmlHandler {

    final private static Logger LOGGER = LoggerFactory.getLogger(MailHtmlHandler.class);

    public static boolean mandarCorreo(String body, String mail, String username) {
        LOGGER.info("Envio de email");
        boolean isEnviado = false;
        // El correo gmail de envío
        String correoEnvia = "smartfitcorporativos@gmail.com";
        //String claveCorreo = "Pragma2019+";
        String claveCorreo = "nrzpiogsvlhclhgz";
        
        // La configuración para enviar correo
        Properties properties = new Properties();
        //properties.put("mail.smtp.host", "smtp.mandrillapp.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        // properties.put("mail.smtp.from", "root@smartfit.com");
        // properties.put("mail.smtp.host", "smtp-mail.outlook.com");

        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", correoEnvia);
        properties.put("mail.password", claveCorreo);

        // Obtener la sesion
        Session session = Session.getInstance(properties, null);

        try {
            // Crear el cuerpo del mensaje
            MimeMessage mimeMessage = new MimeMessage(session);

            // Agregar quien envía el correo
            mimeMessage.setFrom(new InternetAddress("root@smartfit.com", "Convenio SmartFit"));

            // Los destinatarios
            InternetAddress[] internetAddresses = { new InternetAddress(mail)};
            InternetAddress[] recipentCC = {new InternetAddress("recepcioncodigoscorporativos@gmail.com")};

            // Agregar los destinatarios al mensaje
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
            mimeMessage.setRecipients(Message.RecipientType.CC, recipentCC);

            // Agregar el asunto al correo
            mimeMessage.setSubject("¡Hola " + username + "! - Convenio SmartFit.");

            // Crear el multipart para agregar la parte del mensaje anterior
            Multipart multipart = new MimeMultipart();

            // Leer la plantilla
            // convert String into InputStream

            InputStream is = new ByteArrayInputStream(body.getBytes());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            // Almacenar el contenido de la plantilla en un StringBuffer
            String strLine;
            StringBuffer msjHTML = new StringBuffer();
            while ((strLine = bufferedReader.readLine()) != null) {
                msjHTML.append(strLine);
            }

            // Url del directorio donde estan las imagenes

            // String urlImagenes = "C:/Users/brian.uribe/Downloads/Mail (1)/Mail/images/";
            // String urlImagenes = "C:/Users/cmoncada/Downloads/attachments/";
            String urlImagenes = "/home/ubuntu/resources/images/";

            File directorio = new File(urlImagenes);

            // Obtener los nombres de las imagenes en el directorio
            String[] imagenesDirectorio = directorio.list();

            // Creo la parte del mensaje HTML
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msjHTML.toString(), "text/html; charset=UTF-8");

            // Validar que el directorio si tenga las imagenes
            if (imagenesDirectorio != null) {
                for (int count = 0; count < imagenesDirectorio.length; count++) {
                    MimeBodyPart imagePart = new MimeBodyPart();
                    imagePart.setHeader("Content-ID", "<" + imagenesDirectorio[count] + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    imagePart.attachFile(urlImagenes + imagenesDirectorio[count]);
                    // multipart.addBodyPart(imagePart);
                }
            } else {
                LOGGER.info("No hay imagenes en el directorio");
            }

            // Agregar la parte del mensaje HTML al multiPart
            multipart.addBodyPart(mimeBodyPart);

            // Agregar el multipart al cuerpo del mensaje
            mimeMessage.setContent(multipart);

            // Enviar el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(correoEnvia, claveCorreo);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            isEnviado = true;
            LOGGER.info("Email enviado");
        } catch (Exception ex) {
            LOGGER.error("Error al enviar el email", ex);
        }
        return isEnviado;
    }
}
