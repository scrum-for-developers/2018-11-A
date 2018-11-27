package de.codecentric.psd;

import de.codecentric.psd.worblehat.web.VersionProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Worblehat {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Worblehat.class, args);

		System.setProperty("worblehat.version", VersionProvider.getVersion());
		// this code is basically to (a) demonstrate how to stop a Spring application and (b)
		// get rid of the SonarQube warning to close the context properly
		System.out.println("Enter 'stop' to stop Worblehat.");
		String line = "";
		do {
			line = scan.nextLine();
		} while (!line.equals("stop"));
		applicationContext.close();
	}

}
