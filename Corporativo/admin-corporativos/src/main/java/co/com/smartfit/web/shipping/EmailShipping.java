package co.com.smartfit.web.shipping;

public class EmailShipping {

    public static boolean enviarEmailMembresiaVip(String email, String name, String codigo, String logo, String textoEmail) {
        boolean enviado = false;
        String htmlBody = HtmlBody.getHtmlMembresiaVip(name, codigo, "https://www.smartfit.com.co/vip", logo, textoEmail);
        enviado = MailHtmlHandler.mandarCorreo(htmlBody, email, name);
        return enviado;
    }
    
    public static boolean enviarEmailMembresiaConvencional(String email, String name, String codigo, String logo, String textoEmail) {
        boolean enviado = false;
        String htmlBody = HtmlBody.getHtmlMembresiaConvencional(name, codigo, "https://corporativo.smartfitcolombia.com/", logo, textoEmail);
        enviado = MailHtmlHandler.mandarCorreo(htmlBody, email, name);
        return enviado;
    }

    /*
    public static boolean enviarEmailEmpresa(String email, String username, String password) {
        boolean enviado = false;
        String htmlBody = HtmlBody.getHtmlEmpresa(username, password, "https://www.smartfit.com/Admin");
        enviado = MailHtmlHandler.mandarCorreo(htmlBody, email, username);
        return enviado;
    }
    */
}
