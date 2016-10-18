import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JCLinkList {

	
	
	public static void main(String[] args) {
		
		// Array Lists manage arrays internally. Use this one normally.
		ArrayList<Integer> arrayList = new ArrayList<Integer>();	
		
		//Linked List allows to add an item in the middle of the items. It refers to the element previous and next element
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		
		doTimings("ArrayList", arrayList);
		doTimings("LinkedList", linkedList);
		
		
	}
	
	private static void doTimings(String type, List<Integer> list) {
		
		for(int i=0; i<1E5; i++) {
			list.add(i);
			
		}
		
		long start = System.currentTimeMillis();
		
		/*
		for(int i=0; i<1E5; i++) {
			list.add(i);
		}
		*/
		
		for(int i=0; i<1E5; i++) {
			list.add(0,i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " +(end - start) + " ms for " + type);
		
	}
		
}
