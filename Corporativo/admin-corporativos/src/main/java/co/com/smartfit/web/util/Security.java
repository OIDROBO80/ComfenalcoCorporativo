package co.com.smartfit.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * @author alejandro.areiza
 * @since 26/06/2017
 * @version 1.0
 */
public class Security {

    private final String secret = "Sm4rtf1tS3cr3t+-/2017"; // Llave secreta JWT
    private final String emisor = "www.smartfit.com.co"; // Emisor JWT
    private final String algoritmoENC = "HS256"; // Algoritmo usado para la creación del JWT
    private final int minExp = 1 * 60; // Tiempo de expiración del JWT en minutos
    private final static String SHA1 = "SHA1";

    /**
     * Constructor
     */
    public Security() {
        super();
    }

    /**
     * Crea un token
     * 
     * @param userID
     *            Login usuario
     * @param idJWT
     *            Numero unico aleatorio (IDSESION para aplicaciones web, Numero aleatorio para App Movil)
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */

    public String createToken(String userID, String idJWT) throws IllegalArgumentException, UnsupportedEncodingException {
        try {
            Date fechaCreacionJWT = new Date();
            String claveSign = userID + "." + idJWT + "." + sha1(secret);
            return JWT.create().withIssuer(emisor).withSubject(userID).withJWTId(idJWT).withIssuedAt(fechaCreacionJWT)
                    .withExpiresAt(getExpirationDate(fechaCreacionJWT, minExp)).withNotBefore(fechaCreacionJWT)
                    .sign(Algorithm.HMAC256(claveSign));
        } catch (JWTCreationException exception) {
            System.out.println("Creacion de Token Fallida : " + exception.getMessage());
        } catch (Exception e) {
            System.out.println("Creacion de Token Fallida : " + e.getMessage());
        }
        return null;
    }

    /**
     * Metodo que permite
     * 
     * @param
     * @return Date
     */
    private Date getExpirationDate(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     * Verifica un token
     * 
     * @param userID
     * @param idJWT
     * @param token
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */
    // @Override
    public Boolean verifyToken(String userID, String idJWT, String token) {
        try {
            String claveSign = userID + "." + idJWT + "." + sha1(secret);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(claveSign)).withIssuer(emisor).withSubject(userID).withJWTId(idJWT)
                    .build();
            JWT jwt = (JWT) verifier.verify(token);
            if (jwt.getIssuer().equals(emisor) && jwt.getSubject().equals(userID) && jwt.getId().equals(idJWT)
                    && jwt.getAlgorithm().equalsIgnoreCase(algoritmoENC) && jwt.getIssuedAt().compareTo(jwt.getNotBefore()) == 0) {
                return true;
            }
            return false;
        } catch (JWTVerificationException exception) {
            System.out.println("Verificacion Token Fallida : " + exception.getMessage());
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Verificacion Token Fallida : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Verificacion Token Fallida : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Verificacion Token Fallida : " + e.getMessage());
        }
        return false;
    }

    /**
     * Retorna un hash SHA1 a partir de un texto
     * 
     * @param txt
     *            Texto a encriptar
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String txt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA1);
        byte[] array = md.digest(txt.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        String hash = sb.toString();
        return hash;
    }
}
