/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharefinder;
import static com.sun.xml.internal.ws.util.VersionUtil.compare;
import java.util.Scanner;
import java.lang.Object;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *
 * @author admmk0
 */
public class ShareFinder {

    /**
     * @param args the command line arguments
     */
    //static String outFile[][] = new String[100000][7];//0share 1type 2path 3host 4owner 5port 6manager
    //static String inFile[][][] = new String[3][30000][20];//0 shares, 1 servers, 2Permission
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String[] fileNames = new String[3];
        //System.out.println("here0");
        fileNames[0] = "C:\\Users\\admmk0\\Documents\\shares.csv";//getFileName("Shares");//
        //System.out.println("here1"+fileNames[0]);
        fileNames[1] = "C:\\Users\\admmk0\\Documents\\servers.csv";//getFileName("Servers");//
        //System.out.println("here2"+fileNames[1]);
        fileNames[2] = "C:\\Users\\admmk0\\Documents\\permissions.csv";//getFileName("Permissions");//
        //System.out.println("here3"+fileNames[2]);
        String[][] shares = readFile(fileNames[0]);
        //System.out.println("here4");
        String[][] servers = readFile(fileNames[1]);
       // System.out.println("here5");
        String[][] permissions =  readFile(fileNames[2]);
        //System.out.println("here6");
        
        menu(shares,servers,permissions);
        /**
        //for(int i = 0; i <shares.length;i++)
       // System.out.println(shares[i][0]);
        //System.out.println("HI!" +servers[190][0]);
        String[][] outFile = theMagic(shares,servers,permissions);
        //System.out.println("here7");
        String dedup = getProcess("DeDuplicator");
        if(dedup.equals("y")){
        String[][] dedoopFile = removeDup(outFile);
        //System.out.println("here8");
        String reduce = getProcess("Data Reducer");
        if(reduce.equals("y")){
            String[][] reduceFile = dataReduce(dedoopFile);
            writeFile(reduceFile);
        }
        else
        writeFile(dedoopFile);
        }        
        else
            writeFile(outFile);
        //displayFile(outFile);
        */
    }
    
    public static void menu(String[][] shares, String[][] servers, String[][] permissions) throws Exception{
        boolean repeat = true;
        do{
        System.out.println("What do you want to do?");
        System.out.println("1: Compile only");
        System.out.println("2: Compile and remove duplicates");
        System.out.println("3: Summarize");
        System.out.println("4: Compile/remove duplicates/add in all other not found");
        System.out.print("Please choose a number:");
        Scanner lineInPut = new Scanner(System.in);
        String inPut = lineInPut.nextLine();
        String[][] file = new String[50000][8];
        switch(inPut){
            case "1":
                System.out.println("\nWorking...");
                 file = theMagic(shares,servers,permissions);
                break;
            case "2":
                System.out.println("\nWorking...");
                file = theMagic(shares,servers,permissions);
                file = removeDup(file);
                break;
            case "3":
                System.out.println("\nWorking...");
                file = theMagic(shares,servers,permissions);
                file = removeDup(file);
                file = dataReduce(file);
                break;
            case "4":
                System.out.println("\nWorking...");
                file = theMagic(shares,servers,permissions);
                file = removeDup(file);
                file = addNotFound(file,shares);
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
    public static String[][] addNotFound(String[][] inFile, String[][] shares){
        String[][] out = new String[inFile.length][inFile[0].length];
        int t=0;
        boolean found = false;
        for(int i = 0;i<shares.length;i++){//go through every share
            found = false;
            for(int m = 0;m<inFile.length;m++){//compare with inFile
                System.out.println("here" + i);
                if(shares[i][0].equals(inFile[m])){//if share found in list
                    for(int g = 0; g < inFile[m].length;g++){//copy inFile version
                        out[t][g] = inFile[m][g];
                        t++;
                        found = true;//mark as found
                    }
                    System.out.println("here" + m);
                    break;//exit inFile loop
                }
            }
            if(!found){//if share is not found
                out[t][0] = shares[i][0];  //share name
                out[t][1] = shares[i][1];  //share type
                out[t][2] = shares[i][4];  //share path
                t++;
            }
        }
        
        return out;
    }
    
    //remove all the Not Founds
    //0share 1type 2path 3host 4owner 5port 6manager
    public static String[][] removeDup(String[][] outFile){
        //for each share see if host name has a repeating pattern
        String[][] finalFile = new String[outFile.length][outFile[0].length];
        //int sharePointer = 0;
        String currentShare = "";
        String[] repeatingHost = new String[350];
        //System.out.println("here7.1"+ outFile[outFile.length-1][0]);
        for(int i = 0;i<outFile.length-1;i++){
            //System.out.println(i);
            if(outFile[i][0].equals(currentShare)){//if same share
                //System.out.println(i+"here7.2");
                boolean foundHost = false;
                for(int t = 0; t<repeatingHost.length;t++){//check all hosts
                    if(outFile[i][3].equals(repeatingHost[t])||outFile[i][3].equals("-")){
                            foundHost = true;
                            break;
                    }
                    /**
                    if(outFile[i][3]==repeatingHost[t]|| outFile[i][3].equals("-")){//if the same
                        //System.out.println(i+repeatingHost[t]);
                        /**for(int g = 0;g < outFile[i].length;g++){//void you the line
                            System.out.println("out");
                            outFile[i][g] = "-";
                        }*//**
                        System.out.println("here7.5 '"+ outFile[i][3] +"' '"+ repeatingHost[t] + "' " + t);
                        break;
                    }
                    else if(repeatingHost[t].equals("")){//if not same and find blank
                        //if(repeatingHost[t-1].equals(outFile[i][3]))
                           // break;
                        repeatingHost[t] = outFile[i][3];//and new host to list
                        for(int g = 0;g < outFile[i].length;g++){//copy rest of Unique line
                            finalFile[i][g] = outFile[i][g];
                        System.out.println("here7.6 '"+ outFile[i][3] +"' '"+ repeatingHost[t] + "' " + t);
                        
                        }
                        break;
                    }*/
                    //System.out.println(t+repeatingHost[t]);
                }
                if(!foundHost){
                    for(int t = 0; t<repeatingHost.length;t++){
                        if(repeatingHost[t].equals("")){//find blank
                        repeatingHost[t] = outFile[i][3];//and new host to list
                        for(int g = 0;g < outFile[i].length;g++){//copy rest of Unique line
                            finalFile[i][g] = outFile[i][g];
                        }
                        //System.out.println("here7.6 '"+ outFile[i][3] +"' '"+ repeatingHost[t] + "' " + t + " " + outFile[i][0]);
                        break;
                        }
                    }
                }
                
            }
            else{//if not the same share
                //System.out.println("here7.3");
                //sharePointer = i;
                currentShare = outFile[i][0];
                for(int v = 0; v<repeatingHost.length;v++){//reset all host list
                    if(repeatingHost[v]=="")
                        break;
                    else
                        repeatingHost[v]="";
                }
                repeatingHost[0]=outFile[i][3];//add first host to list
                for(int g = 0;g < outFile[i].length;g++){//copy rest of Unique line
                            finalFile[i][g] = outFile[i][g];
                        }
                
            }
            if(outFile[i+1][0] == null){
                for(i+=2;i<outFile.length;i++)
                    if(outFile[i][0] != null){
                        break;
                    }
                i--;
            }
        }
        return finalFile;
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
    public static String[][] array1(String[][] in, String[][] in1){
        //System.out.println("here6.1.1");
        //System.out.println(in1[196][0]);
        int size = (int)((in.length + in1.length));
        String[][] array = new String[size][4];
        int lineNumber = 0;
        //System.out.println("here6.1.2");
        for(int i = 1;i<in.length;i++){//shares
            if(in[i][1] == "*Network*"){
             continue;   
            }
            //System.out.println(in[i][0]+" "+in[i-1][0]);
            if(in[i][0] != null)
            if(in[i][0].equals(in[i-1][0])&&(lineNumber>0)){
                if(!in[i][0].equals(array[lineNumber-1][0]))
                continue;}
            /**
             * Binary search
             
            String[] searchList = new String[in1.length];
            
            for(int y = 0; y < in1.length && (searchList[y] != null || searchList[y] != "null");y++){
                if(in1[y][1] != null || in1[y][1] != "null")
                    searchList[y] = in1[y][1];
               // System.out.println(search[y]);
            }
            int mid = 0;
            if(in[i][0] != null || in[i][0] != "null")
                mid = Arrays.binarySearch(searchList,in[i][0]);
            /**
            int lo = 0;
            int hi = in1.length - 1;
            int mid = lo + (hi - lo) / 2;
            while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
             if(in[i][0].compareTo(in1[mid][1])<0) hi = mid - 1;
             else if (in[i][0].compareTo(in1[mid][1])>0) lo = mid + 1;
             else break;
            }
            int top = mid;
            int bottom = mid;
            while(in1[mid][1] == in1[top][1]){
                top++;
            }
            top--;
            while(in1[mid][1] == in1[bottom][1]){
                bottom--;
            }
            bottom++;
            
            System.out.println(top +" "+bottom);
            * */
            for(int j = 1; j<in1.length;j++){//permission 
                /**
                 * need to add bianary search also need to verify second list is
                 * sorted by what is searched for a-z
                 * may need to figure out how to do it with a find first instance
                 * and a find last then search range.
                 * 
                 * maybe even a a switch to use shorter list as list and longer
                 * list as b
                 */
                if(!(in[i][0] == null || in1[j][1] == null)){
                    //System.out.println("'"+in[i][0]+"' '"+ in1[j][1]+"'");
                if(in[i][0].equals(in1[j][1])){
                    array[lineNumber][0] = in[i][0];  //share name
                    array[lineNumber][1] = in[i][1];  //share type
                    array[lineNumber][2] = in[i][4];  //share path
                    array[lineNumber][3] = in1[j][4];   //host name
                    lineNumber++;
                   // System.out.println("1 "+lineNumber +" " + i + " " +j +in[i][0] + in1[j][1]);
                   // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                }}
            }
        }
        return array;
    }
    public static String[][] array2(String[][] in, String[][] in1){
        //System.out.println("here6.2.1");
        //System.out.println(in[196][0]);
        //System.out.println(in1[5][4]);
        int size = (int)((in.length + in1.length));
        String[][] array = new String[size][4];
        int lineNumber = 0;
        //System.out.println("here6.2.2");
        for(int i = 1;i<in.length;i++){//shares
            //System.out.println(i+" "+in.length);
            for(int j = 1; j<in1.length;j++){//permission 
               // System.out.println("2 "+j+" "+in1.length);
               //System.out.println(in[i][0] +" "+in1[j][4]+" "+i+" "+j);
                if(!(in[i][0] == null || in1[j][4] == null)){
                    //System.out.println(i+" '"+in[i][0]+"' '"+ in1[j][4]+"'");
                if(in[i][0].equals(in1[j][4])){
                    //System.out.println(i +" "+j);
                    array[lineNumber][0] = in1[j][4];   //host name
                    array[lineNumber][1] = in[i][4]; //owner
                    array[lineNumber][2] = in[i][5]; //portfolio
                    array[lineNumber][3] = in[i][6]; //solution manager
                    lineNumber++;
                    //System.out.println("1 "+lineNumber +" " + i + " " +j +in[i][0] + in1[j][4]);
                    //System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
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
        
        //System.out.println("here6.1");
        /**
        for(int i = 0; i <shares.length;i++)
        System.out.println(shares[i][0]);
        for(int i = 0; i <permissions.length;i++)
        System.out.println(permissions[i][0]);
        */
        String[][] sharePer = array1(shares,permissions);
        //System.out.println("HI" +servers[190][0]);
        String[][] perServer = array2(servers,permissions);
       // System.out.println("here6.3");
        String[][] outFile = merge(sharePer, perServer);
        //System.out.println("here6.4");
        
        outFile[0][0] = "Share Name";  //share name
        outFile[0][1] = "Share Type";  //share type
        outFile[0][2] = "Share Path";  //share path
        outFile[0][3] = "Host Name";   //host name
        outFile[0][4] = "Owner";       //owner
        outFile[0][5] = "Portfolio";   //portfolio
        outFile[0][6] = "Solution Manager"; //solution manager
    
        return outFile;
        /**for(int shareLine = 1;shareLine < inFile[0].length;shareLine++){
    if(inFile[0][shareLine][1] == "Network*"){}//do nothing
    else{
    for(int permLine = 1;permLine < inFile[2].length;permLine++){
        if(inFile[0][shareLine][0] == inFile[2][permLine][1]){ //Find every instance of the share in the permission sheet
            for(int serverLine = 1;serverLine < inFile[1].length;serverLine++){
                if(inFile[2][permLine][4] == inFile[1][serverLine][0]){ //Find every instance of the host name in the server sheet
                //display all records found with the share
                    outFile[recordLine][0] = inFile[0][shareLine][0];  //share name
                    outFile[recordLine][1] = inFile[0][shareLine][1];  //share type
                    outFile[recordLine][2] = inFile[0][shareLine][4];  //share path
                    outFile[recordLine][3] = inFile[2][permLine][4];   //host name
                    outFile[recordLine][4] = inFile[1][serverLine][4]; //owner
                    outFile[recordLine][5] = inFile[1][serverLine][5]; //portfolio
                    outFile[recordLine][6] = inFile[1][serverLine][6]; //solution manager
                    recordLine++;
                }
              /** else{
                        
                    outFile[recordLine][0] = inFile[0][shareLine][0];  //share name
                    outFile[recordLine][1] = inFile[0][shareLine][1];  //share type
                    outFile[recordLine][2] = inFile[0][shareLine][4];  //share path
                    outFile[recordLine][3] = inFile[2][permLine][4];   //host name
                    outFile[recordLine][4] = "NOT FOUND"; //cannot find host
                    recordLine++;
                }*/
            }
       /** else{
                outFile[recordLine][0] = inFile[0][shareLine][0];  //share name
                outFile[recordLine][1] = inFile[0][shareLine][1];  //share type
                outFile[recordLine][2] = inFile[0][shareLine][4];  //share path
                outFile[recordLine][3] = "NOT FOUND"; //cannot find share
                recordLine++;
        }*/
    }
    // }}}
//}}
