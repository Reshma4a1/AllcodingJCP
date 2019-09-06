package com.zoopla;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class date {

	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

}
