/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharefinder;
import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author admmk0
 */
public class ShareFinder_Threads {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String[] fileNames = new String[4];
        //System.out.println("here0");
        fileNames[0] = "C:\\Users\\admmk0\\Documents\\shares.csv";//getFileName("Shares");//
        //System.out.println("here1"+fileNames[0]);
        fileNames[1] = "C:\\Users\\admmk0\\Documents\\servers.csv";//getFileName("Servers");//
        //System.out.println("here2"+fileNames[1]);
        fileNames[2] = "C:\\Users\\admmk0\\Documents\\permissions.csv";//getFileName("Permissions");//
        //System.out.println("here3"+fileNames[2]);
        fileNames[3] = "C:\\Users\\admmk0\\Documents\\cifsPer.csv";//getFileName("cifsPer");//
        String[][] shares = readFile(fileNames[0]);
        //System.out.println("here4");
        String[][] servers = readFile(fileNames[1]);
       // System.out.println("here5");
        String[][] permissions =  readFile(fileNames[2]);
        String[][] cifs =  readFile(fileNames[3]);
        //System.out.println("here6");
        
        menu(shares,servers,permissions,cifs);

    }
    public static void menu(String[][] shares, String[][] servers, String[][] permissions,String[][] cifs) throws Exception{
        boolean repeat = true;
        do{
        System.out.println("What do you want to do?");
        System.out.println("1: Compile only");
        System.out.println("2: Compile and remove duplicates");
        System.out.println("3: Summarize");
        System.out.println("4: Compile/remove duplicates/add in all other not found");
        System.out.println("5: Compile/remove duplicates/add CIFS");
        System.out.println("6: Compile/remove duplicates/remove Managment Systems");
        System.out.print("Please choose a number:");
        Scanner lineInPut = new Scanner(System.in);
        String inPut = lineInPut.nextLine();
        String[][] file = new String[50000][8];
        long start;
        long end;
        switch(inPut){
            case "1":
                System.out.println("\nWorking...");
                start = System.currentTimeMillis();
                file = theMagic(shares,servers,permissions);
                end = System.currentTimeMillis();
                System.out.println(end-start);
                break;
            case "2":
                System.out.println("\nWorking...");
                start = System.currentTimeMillis();
                file = theMagic(shares,servers,permissions);
                file = removeDup(file,3);
                end = System.currentTimeMillis();
                System.out.println(end-start);
                break;
            case "3":
                System.out.println("\nWorking...");
                start = System.currentTimeMillis();
                file = theMagic(shares,servers,permissions);
                file = removeDup(file,3);
                file = dataReduce(file);
                end = System.currentTimeMillis();
                System.out.println(end-start);
                break;
            case "4":
                System.out.println("Coming Soon");
                break;
            case "5":
                System.out.println("\nWorking...");
                start = System.currentTimeMillis();
                file = theMagic(shares,servers,permissions);
                file = removeDup(file, 3);
                file = dataReduce(file);
                file = addCifs(file,cifs);
                file[1][0]="Please see Mitchell Kelly for Add Solution Manager";
                end = System.currentTimeMillis();
                System.out.println(end-start);
            case "6":
                System.out.println("\nWorking...");
                start = System.currentTimeMillis();
                file = theMagic(shares,servers,permissions);
                file = removeDup(file,3);
                file = dataReduce(file);
                file = removeTheMan(file);
                end = System.currentTimeMillis();
                System.out.println(end-start);
                break;
        }
        writeDisplay(file);
        System.out.print("Do you want to end program (y/n):");
        inPut = lineInPut.nextLine().toLowerCase();
        if(inPut.equals("y")){
            repeat=false;
        }
        else
            System.out.println("\n\nAll progress may be lost!");
        }while(repeat);
    }
    public static void writeDisplay(String[][] in) throws Exception{
        System.out.print("Do you want to save your file or display on screen (w/d):");
        Scanner lineInPut = new Scanner(System.in);
        String inPut = lineInPut.nextLine().toLowerCase();
        if(inPut.equals("w")){
            writeFile(in);
        }
        else{
            displayFile(in);
            System.out.println("Do you want save display to a file?(y/n)");
            inPut = lineInPut.nextLine().toLowerCase();
            if(inPut.equals("y"))
                writeFile(in);
        }
                
    }
    public static String getProcess(String in){
        Scanner systemInScanner = new Scanner(System.in);
        System.out.printf("Do you want to run the "+ in +" (y/n):");
        String deDup =systemInScanner.nextLine().toLowerCase();
        return deDup;
    }
    public static String getFileName(String fileType){
        Scanner systemInScanner = new Scanner(System.in);
        System.out.printf("Please enter the file name for " + fileType + ":");
        String fileName =systemInScanner.nextLine();
        return fileName;
    }
    public static String[][] readFile(String fileName) throws Exception{
        String[][] inFile = new String[14000][10];
        File readFile = new File(fileName);
        Scanner fileReader = new Scanner (readFile);
        inFile[0] = fileReader.nextLine().split(",");
        int i = 0;
        while(fileReader.hasNext()){
            if(inFile[i][0] == "-" || inFile[i][0] == "" || inFile[i][0] == null)
                i--;
             i++;
            for(int u = 0; u < inFile[i-1].length; u++){
                inFile[i-1][u] = inFile[i-1][u].trim();
            }
            if(fileReader.hasNextLine()){
                inFile[i] = fileReader.nextLine().split(",");
            }
            else
                break;
        }
        fileReader.close();
        return inFile;
    }
    public static void displayFile(String[][] outFile){
        for(int i = 0; i <= outFile.length-1;i++){
            if(outFile[i] == null||outFile[i][0]=="-")
                continue;
            if(!(outFile[i][0]==null)){
            for(int j = 0; j <= outFile[i].length-1; j++){                
                    System.out.print(outFile[i][j] + ", ");
            }
            System.out.print("\n");}
        }
        
    }
    public static void writeFile(String[][] outFile) throws Exception{
        java.io.File fileOut = new java.io.File("C:\\Users\\admmk0\\Documents\\outPut.csv");
        java.io.PrintWriter output = new java.io.PrintWriter(fileOut);
        for(int i = 0; i <= outFile.length-1;i++){
            if(outFile[i] == null||outFile[i][0]=="-")
                continue;
            if(!(outFile[i][0]==null)){
            for(int j = 0; j <= outFile[i].length-1; j++){                
                    output.print(outFile[i][j] + ", ");
            }
            output.print("\n");}
        }
        // close file
        output.close();
        System.out.println("File Writen");

    }
    public static String[][] addCifs(String[][] inFile, String[][] inFileCifs){
        //System.out.println("here6.1.1");
        //System.out.println(in1[196][0]);
        int size = (int)((inFile.length + inFileCifs.length));
        String[][] array = new String[size][8];
        int lineNumber = 0;
        boolean found= false;
        //System.out.println("here6.1.2");
        for(int i = 1;i<inFileCifs.length;i++){//shares
           found = false;
            for(int j = 1; j<inFile.length;j++){//permission 
                if(!(inFile[j][0] == null || inFileCifs[i][1] == null)){
                    //System.out.println("here6.1.3 '"+inFile[j][0]+"' '"+ inFileCifs[i][1]+"'");
                if(inFile[j][0].equals(inFileCifs[i][1])){
                    array[lineNumber][0] = inFile[j][0]; //share name
                    array[lineNumber][1] = inFile[j][2]; //share path
                    array[lineNumber][2] = inFileCifs[i][2]; //share user name
                    array[lineNumber][3] = inFileCifs[i][4]; //permission level
                    for(int g = 4; g <8;g++)
                        array[lineNumber][g] = inFile[j][g];  //existing list 2nd half
                   
                    array[lineNumber][3] = inFileCifs[i][4];   //host name
                    lineNumber++;
                    found = true;
                    //System.out.println("here6.1.4 "+lineNumber +" " + i + " " +j +inFile[j][0] + inFileCifs[i][1]);
                   // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                }
                }
            }
            if(!found){
                  array[lineNumber][0] = inFileCifs[i][1]; //share name
                  array[lineNumber][1] = inFileCifs[i][0]; //share path
                  array[lineNumber][2] = inFileCifs[i][2]; //share user name
                  array[lineNumber][3] = inFileCifs[i][4]; //permission level 
                  array[lineNumber][4] = "Not Found";
                  //System.out.println("here6.1.5");
                  lineNumber++;
                }
        }
        array = removeDup(array, 2);
        return array;
    }
    public static String[][] removeDup(String[][] outFile,int outIndex){
        //for each share see if host name has a repeating pattern
        String[][] finalFile = new String[outFile.length][outFile[0].length];
        //int sharePointer = 0;
        String currentShare = "";//blank share
        Set<String> repeatingHost = new TreeSet<>();
        //System.out.println("here7.1"+ outFile[outFile.length-1][0]);
        for(int i = 0;i<outFile.length-1;i++){       //goes through all data entries in current set
            //System.out.println(i);
            if(outFile[i][0].equals(currentShare)){//if same share
                //System.out.println(i+"here7.2");
                boolean foundHost = false;//will allow quicker execution by allowing skipping rest of excecution once system is found.
                //check for host
                    if(outFile[i][outIndex]==null||outFile[i][outIndex] =="Null"){
                        outFile[i][outIndex]="Not Found";//remove null values and replace with Not Found
                    }
                    if(repeatingHost.contains(outFile[i][outIndex])||outFile[i][outIndex].equals("-")){
                            foundHost = true;//set host found skip next if
                    }
                    //System.out.println(t+ " " +repeatingHost);
                if(!foundHost){//if host not found
                    for(int t = 0; t<repeatingHost.size();t++){
                        repeatingHost.add(outFile[i][outIndex]);//and new host to list
                        for(int g = 0;g < outFile[i].length;g++){//copy rest of Unique line
                            finalFile[i][g] = outFile[i][g];
                        }
                        //System.out.println("here7.6 '"+ outFile[i][3] +"' '"+ repeatingHost + "' " + t + " " + outFile[i][0]);
                        break;
                    }
                }
            }
            else{//if not the same share
                //System.out.println("here7.3");
                //sharePointer = i;
                currentShare = outFile[i][0];//update to new share
                repeatingHost.removeAll(repeatingHost);//reset list
                repeatingHost.add(outFile[i][outIndex]);//add first host to list
                for(int g = 0;g < outFile[i].length;g++){//copy rest of Unique line
                            finalFile[i][g] = outFile[i][g];
                        }
            }
            if(outFile[i+1][0] == null){//if null move to a not null valuse
                for(i+=2;i<outFile.length;i++)
                    if(outFile[i][0] != null){
                        break;
                    }
                i--;//back up to line up with new value
            }
        }
        return finalFile;
    }
    public static String[][] addNotFound(String[][] inFile, String[][] shares){
        String[][] out = new String[inFile.length][inFile[0].length];
        int t=0;
        boolean found = false;
        for(int i = 0;i<shares.length-1;i++){//go through every share
            found = false;
            for(int m = 0;m<inFile.length;m++){//compare with inFile
                //System.out.println("here" + i);
                if(shares[i][0].equals(inFile[m][0])){//if share found in list
                    for(int g = 0; g < inFile[m].length;g++){//copy inFile version
                        out[t][g] = inFile[m][g];
                        t++;
                        found = true;//mark as found
                    }
                    //System.out.println("here" + m);
                    break;//exit inFile loop
                }
            }
            if(!found){//if share is not found
                out[t][0] = shares[i][0];  //share name
                out[t][1] = shares[i][1];  //share type
                out[t][2] = shares[i][4];  //share path
                out[t][3] = "Not Found";   //host not found
                t++;
            }
            if(shares[i+1][0] == null){//if encounter null advance to finish
                for(i+=2;i<shares.length;i++)
                    if(shares[i][0] != null){//if you find a non null
                        break;//continue for loop 
                    }
            }
        }
        
        return out;
    }
    //summarize who has shares on a share
    //0share 1type 2path 3host 4owner 5port 6manager
    public static String[][] dataReduce(String[][] inFile){
        String[][] outFile = new String[inFile.length][inFile[0].length+1];
        String currentShare = "";
        String[][] portfolio = new String[50][inFile[0].length+1];//portfolio line +count
        for(int t = 0;t<inFile[0].length;t++){
            outFile[0][t] = inFile[0][t];
            outFile[0][7] = "Share Count";
        }
        int m = 1;
        for(int i = 0;i<inFile.length-1;i++){
            //System.out.print(inFile[i][0] + " " + currentShare);
            if(inFile[i][0].equals(currentShare)){//if same share
                //System.out.println(i);
                boolean foundPort = false;
                for(int j = 0;j<portfolio.length;j++){//compare portfolios
                    if(inFile[i][5].equals(portfolio[j][5])){                
                        portfolio[j][7]=(Integer.parseInt(portfolio[j][7])+1)+"";//increase count of portfolio on share
                        foundPort = true;//wont make a duplicate portfolio
                        break;
                    }
                }
                if(!foundPort){//if it isnt found add portfolio to list
                    for(int t = 0;t<portfolio.length;t++){
                        if(portfolio[t][0].equals("")||portfolio[t][0].equals(null)){
                            for(int g = 0;g < inFile[i].length;g++){
                                portfolio[t][g] = inFile[i][g];
                            }
                            portfolio[t][7]="1"; // add portfolio count of 1 to end of list
                            break;
                        }
                    }
                    
                }
            }
            else{//if different share
                for(int v = 0; v<portfolio.length;v++){
                    if(portfolio[v][0]=="")//if portfolio is already null stop resting them
                        break;
                    else{
                        for(int t = 0;t<portfolio[v].length;t++){
                            outFile[m][t] = portfolio[v][t];//copy list to final list
                            portfolio[v][t]="";//reset to null
                        }
                        m++;//increase outFile line
                    }
                }
                
                currentShare = inFile[i][0];//set new share
                //System.out.println(currentShare +" " + inFile[i][0] +" " +i);
            }
            if(inFile[i+1][0] == null){//if encounter null advance to finish
                for(i+=2;i<inFile.length;i++)
                    if(inFile[i][0] != null){//if you find a non null
                        break;//continue for loop 
                    }//if it maxes out finished
                i--;//if found non null roll back one (for loop will move it back)
            }
        }
        
        return outFile;
    }
                                    //array1       array2     indexes to output to and to read from    what the comparison index is
    public static String[][] array(String[][] in, String[][] in1, List<Integer> indexOut, int[][] indexIn, List<Integer> compareOn, boolean sort,int lineNumber){
        //System.out.println("here6.1.1");
        //System.out.println(in1[196][0]);
        String[][] array = new String[(in.length + in1.length)][indexOut.size()];
        //int lineNumber = 0;
        //System.out.println("here6.1.2");
        for(int i = 1;i<in.length;i++){//set 1 (in) //remove any network discovered shares
            if(sort){//if sort is enabled
                if(in[i][compareOn.get(0)]==null)
                    continue;
                if(in[i][1] == "*Network*"){
                continue;   
                }
                //System.out.println(in[i][0]+" "+in[i-1][0]);
                if(in[i][compareOn.get(0)] != null)//if not null
                    if(in[i][compareOn.get(0)].equals(in[i-1][compareOn.get(0)])&&(lineNumber>0)){//if the same as the one above(remove duplicates
                        if(!in[i][compareOn.get(0)].equals(array[lineNumber-1][0]))//if already put in list
                            continue;}
                if(in[i][compareOn.get(0)].equals("-"))
                    continue;
            }
            for(int j = 1; j<in1.length;j++){//set 2 (in1)
                if(!(in[i][compareOn.get(0)] == null || in1[j][compareOn.get(1)] == null)){
                    //System.out.println("'"+in[i][0]+"' '"+ in1[j][1]+"'");
                if(in[i][compareOn.get(0)].equals(in1[j][compareOn.get(1)])){
                    for(int v = 0; v < indexOut.size();v++){
                        if(indexIn[v][0]==0)
                            array[lineNumber][indexOut.get(v)] = in[i][indexIn[v][1]];
                        else
                            array[lineNumber][indexOut.get(v)] = in1[j][indexIn[v][1]];
                    }
                    lineNumber++;
                    //System.out.println("1 "+lineNumber +" " + i + " " +j + "__"+in[i][compareOn.get(0)] + "___" + in1[j][compareOn.get(1)]);
                   // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                }}
            }
        }
        return array;
    }
    public static String[][] merge(String[][] array1, String[][] array2){
        int lineNumber = 0;
        //System.out.println(array1[6][3]);
        //System.out.println(array2[6][3]);
        String[][] out = new String[array1.length + array2.length][7];
        for(int i = 0;i<array1.length;i++){//servers
            for(int j = 0; j<array2.length;j++){//permission
                if((array1[i][3] != null && array1[i][3] != "null")&&(array2[j][0] != null && array2[j][0] != "null")){
                //System.out.println(i+" '"+array1[i][3]+"' '"+ array2[j][0]+"'");
                if(array1[i][3] == array2[j][0]){
                    out[lineNumber+1][0] = array1[i][0];  //share name
                    out[lineNumber+1][1] = array1[i][1];  //share type
                    out[lineNumber+1][2] = array1[i][2];  //share path
                    out[lineNumber+1][3] = array1[i][3];   //host name
                    out[lineNumber+1][4] = array2[j][1]; //owner
                    out[lineNumber+1][5] = array2[j][2]; //portfolio
                    out[lineNumber+1][6] = array2[j][3]; //solution manager
                    lineNumber++;
                    //System.out.println("3 "+lineNumber + " " + out[lineNumber][3]);
                }}
            }
        }
        return out;
    }
    //0share 1type 2path 3host 4owner 5port 6manager
    public static String[][] theMagic(String[][] shares,String[][] servers, String[][] permissions){
        //array(String[][] in, String[][] in1, int[] indexOut, int[][] indexIn, int[] compareOn, boolean sort)
        //System.out.println("here6.1");
        LinkedList<Integer> indexOut = new LinkedList<>();
        indexOut.add(0);indexOut.add(1);indexOut.add(2);indexOut.add(3);
        int[][] indexIn = {{0,0},{0,1},{0,4},{1,4}};
        LinkedList<Integer> compare = new LinkedList<>();
        compare.add(0);
        compare.add(1);
        Array sharePerObject = new Array(shares, permissions, indexOut, indexIn, compare, true, 0);
        Thread sharePerThread = new Thread(sharePerObject);
        //String[][] sharePer = array(shares, permissions, indexOut, indexIn, compare, true, 0);//array1(shares,permissions);//
        //System.out.println("HI" +servers[190][0]);
        //indexOut = {0,1,2,3};
        int[][] indexIn1 = {{1,4},{0,4},{0,5},{0,6}};
        compare.remove(1);
        compare.add(4);
        //
        Array perServerObject = new Array(servers, permissions, indexOut, indexIn1, compare, false, 0);
        Thread perServerThread = new Thread(sharePerObject);
        ExecutorService execute = Executors.newCachedThreadPool();
        execute.submit(sharePerThread);
        execute.submit(perServerThread);
        execute.shutdown();
        boolean done;
        do{
            done = execute.isTerminated();
        }while(!done);
        System.out.println("Next");
        String[][] sharePer = sharePerObject.getArray();
        System.out.println(sharePer[5][3]);
        String[][] perServer = perServerObject.getArray();
        //String[][] perServer = array(servers, permissions, indexOut, indexIn1, compare, false, 0);//array2(servers,permissions);//
       // System.out.println("here6.3");
       //indexOut.add(4);indexOut.add(5);indexOut.add(6);
       //int[][] indexIn2 = {{0,0},{0,1},{0,2},{0,3},{1,1},{1,2},{1,3}};
       //compare.remove(1);
       //compare.add(0,3);
       //System.out.println(compare.get(1));
       //System.out.println(compare.get(0));
        String[][] outFile = merge(sharePer, perServer);//array(sharePer, perServer, indexOut, indexIn2, compare, true, 1);//
        //System.out.println("here6.4");
        
        outFile[0][0] = "Share Name";  //share name
        outFile[0][1] = "Share Type";  //share type
        outFile[0][2] = "Share Path";  //share path
        outFile[0][3] = "Host Name";   //host name
        outFile[0][4] = "Owner";       //owner
        outFile[0][5] = "Portfolio";   //portfolio
        outFile[0][6] = "Solution Manager"; //solution manager
    
        return outFile;
        
            }  
    public static String[][] removeTheMan(String[][] file) {
        String[][] out = new String[file.length][file[0].length];
        int line = 0;
        for(int i = 0; i < file.length;i++){
            if(file[i][6] != null)
            if(file[i][6].equals("Steve Royce") || file[i][6].equals("Bruce Jensen"))
                continue;
            else{
                for(int j = 0; j < file[i].length;j++){
                    out[line][j] = file[i][j];
                }
                line++;
            }
        }
        return out;
    }
    }

final class Array implements Runnable{
    String[][] in;
    String[][] in1;
    List<Integer> indexOut;
    int[][] indexIn;
    List<Integer> compareOn;
    boolean sort;
    int lineNumber;
    String[][] array;
    
    @Override
    public void run() {
        magic(in,in1,indexOut,indexIn,compareOn,sort,lineNumber);
        System.out.println("Done");
    }

    public Array() {
    }
    public Array(String[][] in, String[][] in1, List<Integer> indexOut, int[][] indexIn, List<Integer> compareOn, boolean sort,int lineNumber){
        setIn(in);
        setIn1(in1);
        setIndexOut(indexOut);
        setIndexIn(indexIn);
        setCompareOn(compareOn);
        setSort(sort);
        setLineNumber(lineNumber);
        setArray(new String[(in.length + in1.length)][indexOut.size()]);
    }
        public String[][] magic(String[][] in, String[][] in1, List<Integer> indexOut, int[][] indexIn, List<Integer> compareOn, boolean sort,int lineNumber){
        for(int i = 1;i<in.length;i++){//set 1 (in) //remove any network discovered shares
            if(sort){//if sort is enabled
                if(in[i][compareOn.get(0)]==null)
                    continue;
                if(in[i][1] == "*Network*"){
                continue;   
                }
                //System.out.println(in[i][0]+" "+in[i-1][0]);
                if(in[i][compareOn.get(0)] != null)//if not null
                    if(in[i][compareOn.get(0)].equals(in[i-1][compareOn.get(0)])&&(lineNumber>0)){//if the same as the one above(remove duplicates
                        if(!in[i][compareOn.get(0)].equals(array[lineNumber-1][0]))//if already put in list
                            continue;}
                if(in[i][compareOn.get(0)].equals("-"))
                    continue;
            }
            for(int j = 1; j<in1.length;j++){//set 2 (in1)
                if(!(in[i][compareOn.get(0)] == null || in1[j][compareOn.get(1)] == null)){
                    //System.out.println("'"+in[i][0]+"' '"+ in1[j][1]+"'");
                if(in[i][compareOn.get(0)].equals(in1[j][compareOn.get(1)])){
                    for(int v = 0; v < indexOut.size();v++){
                        if(indexIn[v][0]==0)
                            array[lineNumber][indexOut.get(v)] = in[i][indexIn[v][1]];
                        else
                            array[lineNumber][indexOut.get(v)] = in1[j][indexIn[v][1]];
                    }
                    lineNumber++;
                    //System.out.println("1 "+lineNumber +" " + i + " " +j + "__"+in[i][compareOn.get(0)] + "___" + in1[j][compareOn.get(1)]);
                   // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                }}
            }
        }
        System.out.println(Thread.currentThread().getName() + " " + array[5][3]);
        return array;
    }

    public String[][] getIn() {
        return in;
    }

    public void setIn(String[][] in) {
        this.in = in;
    }

    public String[][] getIn1() {
        return in1;
    }

    public void setIn1(String[][] in1) {
        this.in1 = in1;
    }

    public List<Integer> getIndexOut() {
        return indexOut;
    }

    public void setIndexOut(List<Integer> indexOut) {
        this.indexOut = indexOut;
    }

    public int[][] getIndexIn() {
        return indexIn;
    }

    public void setIndexIn(int[][] indexIn) {
        this.indexIn = indexIn;
    }

    public List<Integer> getCompareOn() {
        return compareOn;
    }

    public void setCompareOn(List<Integer> compareOn) {
        this.compareOn = compareOn;
    }

    public boolean isSort() {
        return sort;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String[][] getArray() {
        return array;
    }

    private void setArray(String[][] array) {
        this.array = array;
    }
        
}