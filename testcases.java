//testing input

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;



public class Testcases {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE = "\n";
	private static final String IPADDRESS_DELIMITER = ".";

	public static void writecsvFile(String filename) {

		FileWriter filewriter = null;
		
		Map<Integer, String> first = new HashMap<Integer, String>(); 
		Map<Integer, String> second = new HashMap<Integer, String>(); 

		first.put(1, "outbound");
		first.put(2, "inbound");
		second.put(1, "udp");
		second.put(2, "tcp");


		Random r1 = new Random();
		Random r3 = new Random();
		Random r4 = new Random();

		Random bool = new Random();

		int n1 = r1.nextInt(1);
		int n3 = r3.nextInt(65535) + 1;
		int n4 = r4.nextInt(255);


		try{
			filewriter = new FileWriter(filename);

			for (int i = 0; i < 500000; i++) {
				filewriter.append(first.get(n1));
				n1 = r1.nextInt(2) + 1;
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(second.get(n1));
				n1 = r1.nextInt(2) + 1;
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(n3));
				n3 = r3.nextInt(65535) + 1;
				filewriter.append(COMMA_DELIMITER);
				for (int j = 0; j < 3; j++) {
					filewriter.append(String.valueOf(n4));
					n4 = r4.nextInt(255);
					filewriter.append(IPADDRESS_DELIMITER);
				}
				n4 = r4.nextInt(9);
				filewriter.append(String.valueOf(n4));
				n4 = r4.nextInt(255);
				filewriter.append(NEW_LINE);

			}

			System.out.println("CSV file was created successfully.");

		} catch (Exception e) {
			System.out.println("Error in CSVFileWriter");
			e.printStackTrace();
		} finally {
			try {
				filewriter.flush();
				filewriter.close();
			} catch (IOException e) {
				System.out.println("Error while closing");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Testcases t = new Testcases();
		t.writecsvFile("inputmassiverules.csv");

	} 
		
}
//https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/ 
//Used as basis for writing the CSV input file