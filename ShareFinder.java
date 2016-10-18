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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author admmk0
 */
public class ShareFinder {

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
        String[][] permissions = readFile(fileNames[2]);
        String[][] cifs = readFile(fileNames[3]);
        //System.out.println("here6");

        menu(shares, servers, permissions, cifs);

    }

    public static void menu(String[][] shares, String[][] servers, String[][] permissions, String[][] cifs) throws Exception {
        boolean repeat = true;
        do {
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
            switch (inPut) {
                case "1":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
                case "2":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    file = removeDup(file, 3);
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
                case "3":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    file = removeDup(file, 3);
                    file = dataReduce(file);
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
                case "4":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    file = removeDup(file, 3);
                    file = addNotFound(file, shares);
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
                case "5":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    file = removeDup(file, 3);
                    //file = dataReduce(file);
                    file = addCifs(file, cifs);
                    file[1][0] = "Please see Mitchell Kelly for Add Solution Manager";
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
                case "6":
                    System.out.println("\nWorking...");
                    start = System.currentTimeMillis();
                    file = theMagic(shares, servers, permissions);
                    file = removeDup(file, 3);
                    file = dataReduce(file);
                    file = removeTheMan(file);
                    end = System.currentTimeMillis();
                    System.out.println(end - start);
                    break;
            }
            writeDisplay(file);
            System.out.print("Do you want to end program (y/n):");
            inPut = lineInPut.nextLine().toLowerCase();
            if (inPut.equals("y")) {
                repeat = false;
            } else {
                System.out.println("\n\nAll progress may be lost!");
            }
        } while (repeat);
    }

    public static void writeDisplay(String[][] in) throws Exception {
        System.out.print("Do you want to save your file or display on screen (w/d):");
        Scanner lineInPut = new Scanner(System.in);
        String inPut = lineInPut.nextLine().toLowerCase();
        if (inPut.equals("w")) {
            writeFile(in);
        }
        else if(inPut.equals("q"));
        else {
            displayFile(in);
            System.out.println("Do you want save display to a file?(y/n)");
            inPut = lineInPut.nextLine().toLowerCase();
            if (inPut.equals("y")) {
                writeFile(in);
            }
        }

    }

    public static String getProcess(String in) {
        Scanner systemInScanner = new Scanner(System.in);
        System.out.printf("Do you want to run the " + in + " (y/n):");
        String deDup = systemInScanner.nextLine().toLowerCase();
        return deDup;
    }

    public static String getFileName(String fileType) {
        Scanner systemInScanner = new Scanner(System.in);
        System.out.printf("Please enter the file name for " + fileType + ":");
        String fileName = systemInScanner.nextLine();
        return fileName;
    }

    public static String[][] readFile(String fileName) throws Exception {
        String[][] inFile = new String[14000][10];
        File readFile = new File(fileName);
        Scanner fileReader = new Scanner(readFile);
        inFile[0] = fileReader.nextLine().split(",");
        int i = 0;
        while (fileReader.hasNext()) {
            if (inFile[i][0] == "-" || inFile[i][0] == "" || inFile[i][0] == null) {
                i--;
            }
            i++;
            for (int u = 0; u < inFile[i - 1].length; u++) {
                inFile[i - 1][u] = inFile[i - 1][u].trim();
            }
            if (fileReader.hasNextLine()) {
                inFile[i] = fileReader.nextLine().split(",");
            } else {
                break;
            }
        }
        fileReader.close();
        return inFile;
    }

    public static void displayFile(String[][] outFile) {
        for (int i = 0; i <= outFile.length - 1; i++) {
            if (outFile[i] == null || outFile[i][0] == "-") {
                continue;
            }
            if (!(outFile[i][0] == null)) {
                for (int j = 0; j <= outFile[i].length - 1; j++) {
                    System.out.print(outFile[i][j] + ", ");
                }
                System.out.print("\n");
            }
        }

    }

    public static void writeFile(String[][] outFile) throws Exception {
        java.io.File fileOut = new java.io.File("C:\\Users\\admmk0\\Documents\\outPut.csv");
        java.io.PrintWriter output = new java.io.PrintWriter(fileOut);
        for (int i = 0; i <= outFile.length - 1; i++) {
            if (outFile[i] == null || outFile[i][0] == "-" || outFile[i].equals("null")) {
                continue;
            }
            if (!(outFile[i][0] == null)) {
                for (int j = 0; j <= outFile[i].length - 1; j++) {
                    output.print(outFile[i][j] + ", ");
                }
                output.print("\n");
            }
        }
        // close file
        output.close();
        System.out.println("File Writen");

    }

    public static String[][] addCifs(String[][] inFile, String[][] inFileCifs) {
        //System.out.println("here6.1.1");
        //System.out.println(in1[196][0]);
        int size = (int) ((inFile.length + inFileCifs.length));
        String[][] array = new String[size][8];
        int lineNumber = 0;
        boolean found = false;
        //System.out.println("here6.1.2");
        for (int i = 1; i < inFileCifs.length; i++) {
            found = false;//if Cifs found in inFile
            for (int j = 1; j < inFile.length; j++) {
                if (!(inFile[j][0] == null || inFileCifs[i][1] == null)) {
                    //System.out.println("here6.1.3 '"+inFile[j][0]+"' '"+ inFileCifs[i][1]+"'");
                    if (inFile[j][0].equals(inFileCifs[i][1])) {
                        array[lineNumber][0] = inFile[j][0]; //share name
                        array[lineNumber][1] = inFile[j][2]; //share path
                        array[lineNumber][2] = inFileCifs[i][2]; //share user name
                        array[lineNumber][3] = inFileCifs[i][4]; //permission level
                        for (int g = 4; g < inFile[j].length; g++) {
                            array[lineNumber][g] = inFile[j][g];  //existing list 2nd half
                        }
                        array[lineNumber][3] = inFileCifs[i][4];   //host name
                        lineNumber++;
                        found = true;
                        //System.out.println("here6.1.4 "+lineNumber +" " + i + " " +j +inFile[j][0] + inFileCifs[i][1]);
                        // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                    }
                }
            }
            if (!found) {
                array[lineNumber][0] = inFileCifs[i][1]; //share name
                array[lineNumber][1] = inFileCifs[i][0]; //share path
                array[lineNumber][2] = inFileCifs[i][2]; //share user name
                array[lineNumber][3] = inFileCifs[i][4]; //permission level 
                array[lineNumber][4] = "Not Found";
                //System.out.println("here6.1.5");
                lineNumber++;
            }
            array[0][0] = "Share Name";  //share name
            array[0][1] = "Share Type";  //share type
            array[0][2] = "Share Path";  //share path
            array[0][3] = "Host Name";   //host name
            array[0][4] = "Owner";       //owner
            array[0][5] = "Portfolio";   //portfolio
            array[0][6] = "Solution Manager"; //solution manager
        }
        array = removeDup(array, 2);
        return array;
    }

    public static String[][] removeDup(String[][] outFile, int outIndex) {
        //for each share see if host name has a repeating pattern
        String[][] finalFile = new String[outFile.length][outFile[0].length];
        //int sharePointer = 0;
        Set currentShare = new TreeSet();//blank share
        Set repeatingHost = new TreeSet();
        //System.out.println("here7.1"+ outFile[outFile.length-1][0]);
        for (int i = 0; i < outFile.length - 1; i++) {       //goes through all data entries in current set
            //System.out.println(i);
            if (currentShare.contains(outFile[i][0])) {//if same share
                //System.out.println(i+"here7.2");
                //check for host
                if (outFile[i][outIndex] == null || outFile[i][outIndex] == "Null" || outFile[i][outIndex] == "-") {
                    outFile[i][outIndex] = "Not Found";//remove null values and replace with Not Found
                }
                //System.out.println(i + " " +repeatingHost + " " +repeatingHost.contains(outFile[i][outIndex]));
                if (!repeatingHost.contains(outFile[i][outIndex])) {//if host not found
                    //System.out.println(i + " noNoNO");
                    repeatingHost.add(outFile[i][outIndex]);//and new host to list
                    for (int g = 0; g < outFile[i].length; g++) {//copy rest of Unique line
                        finalFile[i][g] = outFile[i][g];
                    }
                    //System.out.println("here7.6 '"+ outFile[i][3] +"' '"+ repeatingHost + "' " + t + " " + outFile[i][0]);
                }
            } else {//if not the same share
                //System.out.println("here7.3 " + i);
                //sharePointer = i;
                //System.out.println(i + " " + currentShare + " "+ outFile[i][0]+ " " + outFile[i][outIndex]);
                repeatingHost.clear();//reset list
                if (outFile[i][0] != null) {//if null move to a not null valuse
                    //for(i++;i<outFile.length;i++)
                    currentShare.add(outFile[i][0]);//update to new share
                    repeatingHost.add(outFile[i][outIndex]);//add first host to list
                }
                for (int g = 0; g < outFile[i].length; g++) {//copy rest of Unique line
                    finalFile[i][g] = outFile[i][g];
                }
            }
            if (outFile[i + 1][0] == null) {//if null move to a not null valuse
                for (i += 2; i < outFile.length; i++) {
                    if (outFile[i][0] != null) {
                        break;
                    }
                }
                i--;//back up to line up with new value
            }
        }
        return finalFile;
    }

    public static String[][] addNotFound(String[][] inFile, String[][] shares) {
        String[][] out = new String[inFile.length][inFile[0].length];
        int t = 0;
        boolean found;
        for (int i = 0; i < shares.length - 1; i++) {//go through every share
            found = false;

            if (shares[i + 1][0] == null) {//if encounter null advance to nonNull or finish
                for (i += 2; i < shares.length; i++) {
                    if (shares[i][0] != null) {//if you find a non null
                        break;//continue main for loop 
                    }
                }
                continue;
            }
            //System.out.println(i + " " + shares.length);
            if (shares[i][1].equals("Network") || shares[i][1].equals("Network Shared")
                    || ((shares[i][0].contains(":/") || shares[i][0].contains("//")) && shares[i][0] != null && i < shares.length)) {
                continue;
            }
            //System.out.println("1here" + i);
            for (int m = 0; m < inFile.length; m++) {//compare with inFile
                //System.out.println("here" + i);
                if (shares[i][0] == inFile[m][0]) {//if share found in list
                    for (int g = 0; g < inFile[m].length; g++) {//copy inFile version
                        out[t][g] = inFile[m][g];
                    }
                    t++;
                    found = true;//mark as found
                    //System.out.println("here" + m);
                    //break;//exit inFile loop
                }
            }
            //System.out.println("2here" + i);
            if (!found) {//if share is not found
                out[t][0] = shares[i][0];  //share name
                out[t][1] = shares[i][1];  //share type
                out[t][2] = shares[i][4];  //share path
                out[t][3] = "Not Found";   //host not found
                t++;
            }
        }
        out = removeDup(out, 3);
        return out;
    }

    //summarize who has shares on a share
    //0share 1type 2path 3host 4owner 5port 6manager
    public static String[][] dataReduce(String[][] inFile) {
        String[][] outFile = new String[inFile.length][inFile[0].length + 1];
        Set currentShare = new TreeSet();
        TreeMap portfolio = new TreeMap();//protfolio name and count
        for (int t = 0; t < inFile[0].length; t++) {//copies first line
            outFile[0][t] = inFile[0][t];
            outFile[0][7] = "Share Count";
        }
        int tmp = 1;
        int m = 1;
        int lastIteration = 0;
        for (int i = 0; i < inFile.length - 1; i++) {
            if (inFile[i][0] == null) {//if encounter null advance to finish
                for (i++; i < inFile.length; i++) {
                    if (inFile[i][0] != null) {//if you find a non null
                        break;//continue for loop 
                    }//if it maxes out finished
                }
                if (i >= inFile.length)//if maxes out
                {
                    break;
                }
            }
            //System.out.println(i + " " + inFile[i][0] + " " + currentShare);
            if (inFile[i][0] != null) {
                if(currentShare.contains(inFile[i][0])) {//if same share
                    //System.out.println(i);
                    if (portfolio.containsKey(inFile[i][5])) {
                        //System.out.println("h");
                        tmp = (int) portfolio.get(inFile[i][5]);//increase count of portfolio on share
                        tmp++;
                        portfolio.put(inFile[i][5], tmp);
                        //foundPort = true;//wont make a duplicate portfolio
                        //System.out.println("here");
                    } else { //if it isnt found add portfolio to list
                        //System.out.println("e");
                        for (int g = 0; g < inFile[i].length; g++) {
                            outFile[m][g] = inFile[i][g];
                        }
                        m++;
                        portfolio.put(inFile[i][5], 1); // add portfolio count of 1 to end of list
                        //System.out.println("here1");
                    }
                } else {//if different share
                    //System.out.println("r");
                    if (m > 1) {
                        for (int t = m - 1; t >= lastIteration; t--) {
                            if (portfolio.containsKey(outFile[t][5])) //System.out.println(portfolio.get(outFile[t][5]).toString());
                            {
                                outFile[t][7] = portfolio.get(outFile[t][5]).toString();//copy portCount to final
                                System.out.println(portfolio.get(outFile[t][5]).toString() + " " +portfolio.get(outFile[t][5])+ " " + outFile[t][7]);
                            }                    //System.out.println("s");}
                        }
                        lastIteration = m;
                    }
                    //System.out.println(portfolio.values());
                    portfolio.clear();
                    portfolio.put(inFile[i][5], 1);
                    currentShare.add(inFile[i][0]);//set new share
                    for (int g = 0; g < inFile[i].length; g++) {
                        outFile[m][g] = inFile[i][g];
                    }
                    m++;
                    //System.out.println(currentShare +" " + inFile[i][0] +" " +i + " "+ outFile[m-1][2]);
                }
            }

        }

        return outFile;
    }
    //array1       array2     indexes to output to and to read from    what the comparison index is

    public static String[][] array(String[][] in, String[][] in1, List<Integer> indexOut, int[][] indexIn, List<Integer> compareOn, boolean sort, int lineNumber) {
        //System.out.println("here6.1.1");
        //System.out.println(in1[196][0]);
        String[][] array = new String[(in.length + in1.length)][indexOut.size()];
        //int lineNumber = 0;
        //System.out.println("here6.1.2");
        for (int i = 1; i < in.length; i++) {//set 1 (in) //remove any network discovered shares
            if (sort) {//if sort is enabled
                if (in[i][compareOn.get(0)] == null) {
                    continue;
                }
                if (in[i][1] == "*Network*") {
                    continue;
                }
                //System.out.println(in[i][0]+" "+in[i-1][0]);
                if (in[i][compareOn.get(0)] != null)//if not null
                {
                    if (in[i][compareOn.get(0)].equals(in[i - 1][compareOn.get(0)]) && (lineNumber > 0)) {//if the same as the one above(remove duplicates
                        if (!in[i][compareOn.get(0)].equals(array[lineNumber - 1][0]))//if already put in list
                        {
                            continue;
                        }
                    }
                }
                if (in[i][compareOn.get(0)].equals("-")) {
                    continue;
                }
            }
            for (int j = 1; j < in1.length; j++) {//set 2 (in1)
                if (!(in[i][compareOn.get(0)] == null || in1[j][compareOn.get(1)] == null)) {
                    //System.out.println("'"+in[i][0]+"' '"+ in1[j][1]+"'");
                    if (in[i][compareOn.get(0)].equals(in1[j][compareOn.get(1)])) {
                        for (int v = 0; v < indexOut.size(); v++) {
                            if (indexIn[v][0] == 0) {
                                array[lineNumber][indexOut.get(v)] = in[i][indexIn[v][1]];
                            } else {
                                array[lineNumber][indexOut.get(v)] = in1[j][indexIn[v][1]];
                            }
                        }
                        lineNumber++;
                        //System.out.println("1 "+lineNumber +" " + i + " " +j + "__"+in[i][compareOn.get(0)] + "___" + in1[j][compareOn.get(1)]);
                        // System.out.println(array[lineNumber-1][0]+array[lineNumber-1][1]+array[lineNumber-1][2]+array[lineNumber-1][3]);
                    }
                }
            }
        }
        return array;
    }

    public static String[][] merge(String[][] array1, String[][] array2) {
        int lineNumber = 0;
        //System.out.println(array1[6][3]);
        //System.out.println(array2[6][3]);
        String[][] out = new String[array1.length + array2.length][7];
        for (int i = 0; i < array1.length; i++) {//servers
            for (int j = 0; j < array2.length; j++) {//permission
                if ((array1[i][3] != null && array1[i][3] != "null") && (array2[j][0] != null && array2[j][0] != "null")) {
                    //System.out.println(i+" '"+array1[i][3]+"' '"+ array2[j][0]+"'");
                    if (array1[i][3] == array2[j][0]) {
                        out[lineNumber + 1][0] = array1[i][0];  //share name
                        out[lineNumber + 1][1] = array1[i][1];  //share type
                        out[lineNumber + 1][2] = array1[i][2];  //share path
                        out[lineNumber + 1][3] = array1[i][3];   //host name
                        out[lineNumber + 1][4] = array2[j][1]; //owner
                        out[lineNumber + 1][5] = array2[j][2]; //portfolio
                        out[lineNumber + 1][6] = array2[j][3]; //solution manager
                        lineNumber++;
                        //System.out.println("3 "+lineNumber + " " + out[lineNumber][3]);
                    }
                }
            }
        }
        return out;
    }

    //0share 1type 2path 3host 4owner 5port 6manager
    public static String[][] theMagic(String[][] shares, String[][] servers, String[][] permissions) {
        //array(String[][] in, String[][] in1, int[] indexOut, int[][] indexIn, int[] compareOn, boolean sort)
        //System.out.println("here6.1");
        LinkedList<Integer> indexOut = new LinkedList<>();
        indexOut.add(0);
        indexOut.add(1);
        indexOut.add(2);
        indexOut.add(3);
        int[][] indexIn = {{0, 0}, {0, 1}, {0, 4}, {1, 4}};
        LinkedList<Integer> compare = new LinkedList<>();
        compare.add(0);
        compare.add(1);
        String[][] sharePer = array(shares, permissions, indexOut, indexIn, compare, true, 0);//array1(shares,permissions);//
        //System.out.println("HI" +servers[190][0]);
        //indexOut = {0,1,2,3};
        int[][] indexIn1 = {{1, 4}, {0, 4}, {0, 5}, {0, 6}};
        compare.remove(1);
        compare.add(4);
        //
        String[][] perServer = array(servers, permissions, indexOut, indexIn1, compare, false, 0);//array2(servers,permissions);//
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
        for (int i = 0; i < file.length; i++) {
            if (file[i][6] != null) {
                if (file[i][6].equals("Steve Royce") || file[i][6].equals("Bruce Jensen")) {
                    continue;
                } else {
                    for (int j = 0; j < file[i].length; j++) {
                        out[line][j] = file[i][j];
                    }
                    line++;
                }
            }
        }
        return out;
    }
}
