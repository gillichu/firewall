
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class csvReader {
	BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";
    String csvSplitRange = "-";

    public csvReader(String myfilepath, DecisionTrie d) {
	    try {

	    	System.out.println("hello");

	            br = new BufferedReader(new FileReader(myfilepath));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] rule = line.split(csvSplitBy);
	                int limit = rule.length;

	                String port = rule[2];
	                String ipAddress = rule[3];

	                String[] portRange = new String[1];
	                String[] ipAddressRange = new String[1];

	                if (port.contains(csvSplitRange)) {
	                	System.out.println("Splitting Range");

	                // //check if port is a range (going to hardcode for now)
	                portRange = rule[2].split(csvSplitRange);
		                for (int i = 0; i < portRange.length; i++){
		                	System.out.println(portRange[i]);
		                }
	                }

	                if (ipAddress.contains(csvSplitRange)){
		                // //check if ipAddress is a range
		                ipAddressRange = rule[3].split(csvSplitRange);
	                }

	                Object[] rules = new Object[4];

	                for (int i = 0; i < limit; i++){
	                	rules[i] = rule[i];
	                	if (i == 2){
	                		rules[i] = portRange;
	                		System.out.println(rules[i] + " " + " PUT IN PORT RANGE");
	                	} else if (i == 3){
	                		rules[i] = ipAddressRange;
	                		System.out.println(rules[i] + " " + " PUT IN IPADDRESS RANGE");

	                	}
	                }

	                System.out.println("printing new rule: ");
	                for (int i = 0; i < limit; i++){
	                	// if (i==2 || i==3){
	                	// 	for (int j = 0; j < rule[i].length; j++){
	                	// 		System.out.print(rule[i][j] + " ");
	                	// 	}
	                	// }
	                	System.out.print(rules[i] + " ");
	                }
	                System.out.println();

	                d.inputRule(rules);

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
    }
}