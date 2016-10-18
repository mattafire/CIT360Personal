
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/*
 *
/**
 *
 * @author Sarah
 */
      
class Student implements Serializable {  			 //This writes to a binary file
    String name;
        
    public Student(String name) {        			 //Constructor
    this.name = name;
        }
    
    @Override
    public String toString() {           			 //The to string method
        return "Student [name=" + name + "]";   	 //prints out name when deserializing
    }
    
    }

public class JSONSerialization {
    public static void main(String[] args) {
        
        Student sarah = new Student("Sarah Kelly");  //Student object
       
         try {                                       //needs try catch so it won't error out (or compile right)
            writeToFile(sarah); 					 //passes in student.
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
        
         
            try {
                readFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }    
        
    }
    
public static void writeToFile(Student s) throws IOException{  //needs to be static so you can refer it up 
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Student.bin"));   //Object Output Stream. It writes to binary file. Also need file output stream
    
    objectOutputStream.writeObject(s);
}    
    
public static void readFile()throws IOException { 
    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Student.bin")); //gets binary file into Object.
    
    Student name = null;
	try {
		name = (Student) objectInputStream.readObject();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} //need to cast Student 

    System.out.println(name);


}


}    
    
    
    

