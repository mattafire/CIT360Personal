import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class JCSet {

	public static void main(String[] args) {
	// does not retain order	
	//	Set<String> set1 = new HashSet<String>();
		
	// remembers order you added in
	//	Set<String> set1 = new LinkedHashSet<String>();
	
	//sorts in natural order	
		Set<String> set1 = new TreeSet<String>();
		
		if(set1.isEmpty()) {
			System.out.println("Set is empty at start");
		}
		
		set1.add("dog");
		set1.add("cat");
		set1.add("mouse");
		set1.add("bird");
		set1.add("bear");
		
		if(set1.isEmpty()) {
			System.out.println("Set is empty after added (no!)");
		}
		
		System.out.println(set1);
		
		//iteration//
		for(String element: set1) {
			System.out.println(element);
		}
		
		//does set contain a given item?//4

		if(set1.contains("tiger")) {
			System.out.println("contains tiger");
		}
		
		if(set1.contains("cat")) {
			System.out.println("contains cat");
		}
		
	//set2 contains some common elements with set1, and some new		
		Set<String> set2 = new TreeSet<String>();
		
		set2.add("dog");
		set2.add("cat");
		set2.add("cow");
		set2.add("monkey");
		set2.add("mitch");
		
		
		//interesection///
		Set<String> intersection = new HashSet<String>(set1);
		
		intersection.retainAll(set2);
		
		System.out.println(intersection);
		
		//difference///
		Set<String> difference = new HashSet<String>(set1);
		
		difference.removeAll(set2);
		System.out.println(difference);
		
				
	}

}
