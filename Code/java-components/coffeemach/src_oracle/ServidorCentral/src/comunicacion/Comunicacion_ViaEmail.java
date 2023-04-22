package comunicacion;

import java.util.Properties;

public class Comunicacion_ViaEmail {

	private Properties properties;

	public Comunicacion_ViaEmail() {

	}

	public void prueba() {
		System.out.println("***Entra***");
	}

	public void enviarEmail(String mensaje, String email) {

		// try {
		//
		// String msjFinal="\n---------------------------------------" +
		// "\n Este es un servicio automático, por favor no responda a este mensaje"
		// +
		// "\n\n<b>PD:Recuerde que debe atender la solicitud en el menor tiempo posible. La hora de activación"
		// +
		// "de la máquina de café asignada será reportada de manera automática</b>";
		// mensaje+=msjFinal;
		//
		// String mailRServer="remoteservercentral@gmail.com";
		// String password="swArch1cesi";
		// String subject="Notificación de alerta de maquina de café";
		// properties=new Properties();
		//
		// properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		// properties.setProperty("mail.smtp.starttls.enable", "true");
		// properties.setProperty("mail.smtp.port", "587");
		// properties.setProperty("mail.smtp.user", mailRServer);
		// properties.setProperty("mail.smtp.auth", "true");
		//
		// javax.mail.Session session =
		// javax.mail.Session.getDefaultInstance(properties);
		// javax.mail.internet.MimeMessage message = new
		// javax.mail.internet.MimeMessage(session);
		// message.setFrom(new
		// javax.mail.internet.InternetAddress(mailRServer));
		// message.addRecipient(javax.mail.Message.RecipientType.TO, new
		// javax.mail.internet.InternetAddress(email));
		// message.setSubject(subject);
		// message.setContent(mensaje, "text/html");
		//
		// javax.mail.Transport t = session.getTransport("smtp");
		// t.connect(mailRServer, password);
		// t.sendMessage(message, message.getAllRecipients());
		// t.close();
		//
		// } catch (javax.mail.internet.AddressException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (javax.mail.MessagingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
