
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


//10:20 AM Initial Thoughts: 
	//Build a sort of trie decision tree? But more commonly used rules won't have a faster processing time?
	//Researching how firewalls are built
//10:25 AM Learning about iptables, found the filter table
//10:41 AM Set up the CSV reader, as far as I can tell I can't implement an iptable, so I'll have to build a similar one
//10:48 switching from java to python, found a python library that can implement iptables
//11:55 Reread the specs, can't use special python libraries, and i'm pretty sure that qualifies
//11:22 Finished building the decision trie, if I had more time I would definitely go learn more about how the iptable is built
//11:35 Writing accept_packet, moving on to work on reading in ranges, might run out of time though
//12:00 Went a bit over because I rewrote the checkPass to be recursive, but still having errors with the checkPass for port range
