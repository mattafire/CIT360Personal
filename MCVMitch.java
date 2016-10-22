
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mattafire
 */
public class MCVMitch {

    public static void main(String[] arugs){
        myControl.doStuff();
    }
}

class myModel {

    static int num = 0;
    static String name = new String();

    public myModel() {
    }

    public static int getNum() {
        return num;
    }

    public static void setNum(int num) {
        myModel.num = num;
    }
    
    public static void addNum(int num) {
        myModel.num = num + myModel.num;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        myModel.name = name;
    }

}

class myView {

    public myView() {
    }

    public static void display(String out) {
        System.out.print(out);
    }
}

class myControl {
    
    public myControl() {
        
    }

    public static void toView(String out) {
        myView.display(out);
    }

    public static void getIn(String type) {
        Scanner lineInPut = new Scanner(System.in);
        String inPut = lineInPut.nextLine();
        if(type.equals("name"))
            myModel.setName(inPut);
        if(type.equals("num"))
            myModel.addNum((Integer.parseInt(inPut)));
    }
    
    public static void doStuff(){
        toView("What is your name?");
        getIn("name");
        toView("Enter a number:");
        getIn("num");
        toView(myModel.getName() + " entered "+ myModel.getNum());
    }
}
