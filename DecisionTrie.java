import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;

class DecisionTrie {
	public int level;

	class TrieNode {
		Map<Object, TrieNode> colValues = new HashMap<Object, TrieNode>(); 

		public void printTrieNode(){
			for (Object k : colValues.keySet()){
				System.out.print(k + " ");
				colValues.get(k).printTrieNode();
			}
		}
	}

	static TrieNode root;


	public DecisionTrie(){
		level = 0;
		root = new TrieNode();
	}

	public void inputRule(Object[] rule){
		TrieNode curr = root;
		for (int i = 0; i < rule.length; i++){
				Object val = rule[i];
			
				//check if this option exists already
				if (curr.colValues.keySet().contains(val)) {
					curr = curr.colValues.get(val);
				}
				else {
					TrieNode nextLevel = new TrieNode();

					//check whether value is a range

					curr.colValues.put(val, nextLevel);
					curr = nextLevel;
				}
			level += 1;
		}
		//printTrie();
	}

// check recursively so that if it fulfills multiple rules it can 
	public boolean checkPass(Object[] specs, TrieNode n) {
		System.out.println("inside checkPass");
		Set k = n.colValues.keySet();
		Object spec = specs[0];

		Object key = new Object(); 
		for (Object l : k){
			key = l;
		}

		Object[] newArray = new Object[specs.length - 1];
		System.arraycopy(specs, 1, newArray, 0, specs.length-1);

		//System.out.println("HOPEFULLY THIS IS TRUE: " + key + " " + (key instanceof String[]));
		boolean results = false;
		if (specs.length != 0){
			System.out.println("SPEC: " + spec + " HAS? " + k.contains(spec));
			if (k.contains(spec)) {
				results = results || checkPass(newArray, n.colValues.get(spec))?true:false;
			} 
			else if ((key instanceof String[])) {
				for (Object pairing : k) {
					if (inRange(pairing, spec)) {
						results = results || checkPass(newArray, n.colValues.get(pairing));
					}
				}
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
		return results;
	}

	public boolean inRange(Object pairing, Object spec){
		System.out.println("INSIDE INRANGE");
		int[] range = new int[2];
		int[] ipAddresses = new int[2];
		int ipAd = 0;

		String[] r = (String[]) pairing;
		for (int i = 0; i < r.length; i++){
			//attempts to parse the ip addresses
			// if (r[i].contains(".")) {
			// 	r[i] = (String) r[i]
			// 	ipAddresses = r[i].split(".");
			// 	ipAd = Integer.parseInt(ipAdresses[ipAdresses.length-1]);
			// }
			// else {
				range[i] = Integer.parseInt(r[i]);
			// }
		}
		int num = (int) spec;
		if (num <= Math.max(range[0], range[1]) && num >= Math.min(range[0], range[1])){
			return true;
		}
		// else if (check for the IPAddress range here, saved under ipAd)
		return false;
	}

	public boolean checkSpec(Object[] specs){
		return checkPass(specs, root);
	}

	public void printTrie(){
		System.out.println("printTrie called");
		root.printTrieNode();
	}
}
