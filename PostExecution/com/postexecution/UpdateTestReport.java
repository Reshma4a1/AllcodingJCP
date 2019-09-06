package com.postexecution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.repository.FileRepository;


public class UpdateTestReport {
	static Document doc = null;

	private static void updateHTMLReport() {
		try {
			doc = Jsoup.parse(new File(FileRepository.SERVER_HTML_REPORT_FILE_PATH), "UTF-8");
			
			for (Element child : doc.select("body").select("*")) {
				String className = child.className().toLowerCase();
				if (className.equals("logo-container blue darken-2")) {
					child.remove();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void convertToHtml() {
		StringBuilder htmlStringBuilder = new StringBuilder();
		htmlStringBuilder.append(doc.toString());
		File file = new File(FileRepository.SERVER_HTML_REPORT_FILE_PATH);
		try {
			// write to file with OutputStreamWriter
			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer = new OutputStreamWriter(outputStream);
			writer.write(htmlStringBuilder.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void sendUpdatedReport(){
		try{
			updateHTMLReport();
			convertToHtml();
			new EmailService().sendEmail();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is for main thread test
	 */
	public static void main(String args[]){
		try{
			sendUpdatedReport();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
