package com.light;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		
		Set<Object> s1 = new TreeSet<Object>();
		s1.add(123);
		s1.add(135l);
	}
}
