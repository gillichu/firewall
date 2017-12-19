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
				System.out.println(k + " ");
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
				
				// //checking to make sure transferred correctly
				// if (i == 2 || i == 3) {
				// 	String[] yo = (String[]) val;
				// 	System.out.println("AS IM INPUTTING " + i + " " + yo[0] + " " + yo[1]);
				// }

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
		// System.out.println("inside checkPass");
		if (specs.length == 0) {
			return true;
		}
		Set k = n.colValues.keySet();
		Object spec = specs[0];

		Object key = new Object(); 
		for (Object l : k){
			key = l;
		}

		Object[] newArray = new Object[specs.length - 1];
		System.arraycopy(specs, 1, newArray, 0, specs.length-1);
		// System.out.println("current newArray length = " + newArray.length);

		//System.out.println("HOPEFULLY THIS IS TRUE: " + key + " " + (key instanceof String[]));
		boolean results = false;

		printKeySet(k);

		if (specs.length != 0){
			// for (Object thing : k) {
			// 	System.out.println("k is "+ thing + " of type " + thing.getClass() + " spec is " + spec + "of type " + spec.getClass() +  " equals? " + k.equals(spec));
			// }
			if (k.contains((Object) spec)) {
				System.out.println("SPEC: " + spec + " HAS? " + k.contains(spec));
				results = results || checkPass(newArray, n.colValues.get(spec))?true:false;
			}

			//if the key is a range
			else if (key instanceof String[]) {
				for (Object pairing : k) {
					if (newArray.length == 0) {
						System.out.println("SPEC: " + spec + " HAS IP: " + inIPRange(pairing, spec));

						if (inIPRange(pairing, spec)) {
							results = results || checkPass(newArray, n.colValues.get(pairing));

						}
					}
					else if (inPortRange(pairing, spec)) {
						System.out.println("SPEC: " + spec + " HAS PortRange TRUE" );

						results = results || checkPass(newArray, n.colValues.get(pairing));
					}
				}
			}
			 //if the spec is a port
			else if (spec instanceof Integer) {
				String t = Integer.toString((int) spec);
				System.out.println("SPEC: " + spec + " HAS? " + k.contains(t));

				if (k.contains(t)) {
					results = results || checkPass(newArray, n.colValues.get(t))?true:false;
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

	public void printKeySet(Set k) {
		System.out.println("This is the set of things in keySet");
		for (Object thing : k) {
			System.out.print(thing + ", ");
		}
		System.out.println(" END");
	}

	public boolean inPortRange(Object pairing, Object spec){
		//System.out.println("INSIDE IN PORTRANGE");
		int[] range = new int[2];

		//cast the Object back into a String array of the range
		String[] r = (String[]) pairing;
		for (int i = 0; i < r.length; i++){
			// System.out.println("I'm in here with " + r[i]);
				range[i] = Integer.parseInt(r[i]);

		}
		int num = (int) spec;
		// System.out.println("Inside mathrange for portrange");
		if (num <= Math.max(range[0], range[1]) && num >= Math.min(range[0], range[1])){
			return true;
		}
		return false;
	}

	public boolean inIPRange(Object pairing, Object spec) {
		System.out.println("INSIDE IP RANGE");

		String[] stringified = (String[]) pairing;
		//System.out.println(stringified[0] + " " + stringified[1]);

		//split string by period
		String[] parsed1 = stringified[0].split("\\.");
		String[] parsed2 = stringified[1].split("\\.");

		String in = (String) spec;
		String[] parsedSpec = in.split("\\.");

		//convert strings to ints
		int[] range1 = parseArray(parsed1);
		int[] range2 = parseArray(parsed2);
		int[] intSpec = parseArray(parsedSpec);

		boolean result = true;
		boolean samesofar = true;
		//assuming the two range 'octets' are in order / well-formed
		for (int i = 0; i < range1.length; i++) {
			//System.out.println("round " + i + " range1: " + range1[i] + " intSpec: " + intSpec[i] + " range2: " + range2[i]);
			
			if (range1[i] != range2[i]) {
				samesofar = false;
			}

			if (range1[i] <= intSpec[i] && intSpec[i] <= range2[i]) {
				//System.out.println("within range");
				result = result && true;
			} else if (!(samesofar)) {
				//System.out.println("not same so far");
				result = result && true;
			} else {
				//System.out.println("declaring false");
				result = result && false;
			}
		}
		return result;
	}

				// System.out.println("starting");
				// System.out.println(range1[i] <=range2[i]);
				// System.out.println(intSpec[i] >= range1[i]);
				// System.out.println(" here " + intSpec[i] + " " + range2[i]);

	public int[] parseArray(String[] inIP) {
		int[] range = new int[4];

		for (int j=0; j < inIP.length; j++) {
			range[j] = Integer.parseInt(inIP[j]);
			// System.out.println(range[j]);
		}
		return range;
	}

	public boolean checkSpec(Object[] specs){
		return checkPass(specs, root);
	}

	public void printTrie(){
		System.out.println("printTrie called");
		root.printTrieNode();
	}
}
