package de.fk.notes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	public Parser() {
		try {
			InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/testfile.txt"));
			String string = Files.readString(
					FileSystems.getDefault().getPath("C:\\Users\\ub8kef\\daten\\workspaces\\eap\\Notes\\src\\", "testfile.txt"));
			System.out.println(string);
			var p1 = Pattern.compile("#([\\w]*)");
			Matcher m1 = p1.matcher(string);
			while (m1.find()) {
				System.out.println(m1.group(1));
			}

			var p2 = Pattern.compile("[ox\\*-]\\s(\\w*)");
			Matcher m2 = p2.matcher(string);
			while (m2.find()) {
				System.out.println(m2.group(1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Parser();
	}

}
