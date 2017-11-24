
public class Firewall {
	DecisionTrie d;
	//constructor, taking single string argument
	public Firewall(String filepath){
		d = new DecisionTrie();
		//read CSV file into a DecisionTrie
		csvReader c = new csvReader(filepath, d);

	}

	// return true: there exists a rule in file this object was initialized with that allows this packet
	// otherwise false
	public boolean accept_packet(String direction, String protocol, Integer port, String ipAddress){
	// direction (string): "inbound" or "outbound"
	// protocol (string): exactly one of "tcp" or "udp", all lowercase
	// port (integer) - an integer in the range [1, 65535]
	// ip_address (string): a single wellformed IPv4 address.
		Object[] specs = new Object[4];
		specs[0] = direction;
		specs[1] = protocol;
		specs[2] = port;
		specs[3] = ipAddress;

		for (int i = 0; i < specs.length; i++){
			System.out.print(specs[i]);
		}

		return d.checkSpec(specs);
	}


	public static void main(String[] args) {
		Firewall f = new Firewall("/Users/gillianchu/Desktop/Firewall/inputfile.csv");
		System.out.println(f.accept_packet("outbound", "tcp", 10000-20000, "192.168.10.11"));
		System.out.println(f.accept_packet("outbound", "tcp", 12000, "192.168.10.11"));
	} 

}


//Functionality (write test scripts)
//code clarity (oop where appropriate)
	//easy to understand code; descriptive variables and functions?
	//tricky areas of code well-commented?

//performance : tradeoffs between space and time complexity is a core component
// think about performance instead of settling for the naive soln
//expect the code to work "reasonably quickly"; not appear unresponsive (500K-1M items) after the dataset has been loaded (i.e. after constructor has returned)
