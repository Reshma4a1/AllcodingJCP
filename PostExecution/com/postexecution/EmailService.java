package com.postexecution;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.logconfig.LogConfig;
import com.repository.FileRepository;
import com.util.Base64Encode;
import com.util.PropertyReader;



public class EmailService {

	static String host;
	static String port;
	static String mailFrom_Id;
	static String mailFrom_password;
	static String mailTo_Id;
	static String subject;
	static String message;
	static boolean sendEmailFlag;
	Map<String,String> testData =null;
	static Logger consoleLog= LogConfig.getLogger(EmailService.class);
	
	public EmailService(){
		try {
			testData=PropertyReader.getData(FileRepository.SERVER_GLOBAL_PROPERTY);
			host = testData.get("host");
			port = testData.get("port");
			mailFrom_Id = testData.get("mailFrom_Id");
			mailFrom_password = Base64Encode.getDecodeString(testData.get("mailFrom_password"));
			mailTo_Id = testData.get("mailTo_Id");
			subject = "JARVIS Completed Your Automation Execution "+ DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDate.now())+" "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
			message = "Hi All,<br>"
					   +"<br> *** This is an auto generated mail from JARVIS Automation Enginee, please do not reply to this mail. ***"
					   +"<br>"
					   +"<br>"
					   + "<br>Automation Execution Completed at "+ DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDate.now())+" "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())
					   + "<br>Reports are Attached."
					   +"<br>"
					   +"<br>"
					   +"(For Best View Please Open In Chrome Browser)"
					   +"<br>Regards,"
					   +"<br>JARVIS Team";
			consoleLog.info("Message Body initialised");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean sendEmail() throws Exception {
		try {
			Properties properties = getSMTPProperties();
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailFrom_Id, mailFrom_password);
				}
			};
			Session session = Session.getInstance(properties, auth);
			Message msg = getMimeMessage(session,mailFrom_Id,mailTo_Id,message,subject);
			Transport.send(msg);
			consoleLog.info("Execution Report Email Sent");
			sendEmailFlag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sendEmailFlag;
	}

	private static final Message getMimeMessage(Session session,String mailFrom,String mailTo,String message,String subject) throws Exception{
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mailFrom));
		String[] recipientList = mailTo.split(",");
		InternetAddress[] toAddresses = new InternetAddress[recipientList.length];
		int counter = 0;
		for (String recipient : recipientList) {
			toAddresses[counter] = new InternetAddress(recipient.trim());
			counter++;
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(message);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart.setContent(message, "text/html");
		messageBodyPart = new MimeBodyPart();
        String filename = FileRepository.SERVER_HTML_REPORT_FILE_PATH;
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
		return msg;
	}
	
	private static final Properties getSMTPProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "false");
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.user", mailFrom_Id);
		properties.put("mail.password", mailFrom_password);
		return properties;
	}


}
