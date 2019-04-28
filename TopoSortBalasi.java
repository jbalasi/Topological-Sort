//Jen Balasi
//CSC 406
//A3P2

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.util.Stack;

public class TopoSortBalasi {
    private static PrintWriter writer;
    private static Scanner scanner;
    private static LinkedList<Integer> list[];
    private static int size = 0;
    private static Stack<Integer> stack;
    private static int inDegree[];
    private static int timeArray[];

    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("There were no arguments passed in.");
            System.exit(0);
        }
        try {
            Scanner scanner;
            File fileIn = new File(args[0]);
            File fileOut = new File("C:\\Users\\JJ\\IdeaProjects\\TopoSort\\src\\output.txt");
            writer = new PrintWriter(fileOut);
            createDiGraph(fileIn);
            sort();
            criticalTask();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDiGraph(File dataFile) {
        int start;
        int finish;

        try {
            scanner = new Scanner(dataFile);
            size = scanner.nextInt() + 1;
            list = new LinkedList[size];
            for (int i = 0; i < size; i++) {
                list[i] = new LinkedList<Integer>();
            }
            while (scanner.hasNext()) {
                start = scanner.nextInt();
                finish = scanner.nextInt();
                list[start].addFirst(finish);
                if (list[finish].contains(start)) {
                    writer.println("The graph is cyclic");
                    writer.close();
                    System.exit(0);
                }
            }
            for (int i = 0; i < size; i++) {
                if (list[i].size() > 0) {
                    writer.print("n" + i + " points to: ");
                    for (int j = 0; j < list[i].size(); j++) {
                        writer.print(list[i].get(j) + " ");
                    }
                    writer.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sort() {
        inDegree = new int[list.length];

        for (int i = 0; i < size; i++) {
            if (list[i].size() > 0) {
                for (int j = 0; j < list[i].size(); j++) {
                    inDegree[list[i].get(j) - 1] += 1;//adds all inDegrees to an array in order
                }
            }
        }
        writer.println();
        writer.print("\t");
        for (int i = 1; i < size; i++) {
            writer.print("n" + i + "\t");//node location
        }
        writer.println();
        for (int i = 0; i < inDegree.length - 1; i++) {
            writer.print("\t" + inDegree[i]);//nodes in-degrees
        }
        writer.println();
        inDegreeZero();
    }

    private static void inDegreeZero() {
        stack = new Stack<Integer>();
        String str = " ";
        int count = 0;
        for (int u = 0; u < inDegree.length - 1; u++) {//find inDegree and pass to stack
            if (inDegree[u] == 0) {
                str = str + " " + (u + 1);
                stack.push(u);
                count++;
                while (!stack.empty() && count < size) {
                    u = stack.pop();
                    for (int i = 0; i < inDegree.length - 1; i++) {
                        if (list[i].size() > 0) {
                            for (int j = 0; j < list[i].size(); j++) {
                                if (inDegree[list[i].get(j) - 1] == 0) {
                                    stack.push(list[i].get(j));
                                }
                                inDegree[list[i].get(j) - 1] -= 1;
                            }
                            for (int x = 0; x < inDegree.length - 1; x++) {
                                writer.print("\t" + inDegree[x]);//nodes in-degrees
                                count += inDegree[x];
                            }
                        }
                        writer.println("");
                    }
                }
            }
        }
        writer.print("Stack " + str);
    }

    private static void criticalTask() {
        timeArray = new int[]{3, 2, 1, 6, 4, 5, 0};
        String predArray[] = new String[size];
        int eft[] = new int[size];


        for (int i = 0; i < size; i++) {
            predArray[i] = " ";
            eft[i]=timeArray[i];
        }
        writer.print("\n\nTasks\t\t\t");
        for (int i = 1; i < size; i++) {
            writer.print("n" + i + "\t\t");
        }
        writer.print("\nPredecessors\t");
        for (int i = 0; i < size; i++) {
            if (list[i].size() > 0) {
                for (int j = 0; j < list[i].size(); j++) {
                    predArray[list[i].get(j)] = predArray[list[i].get(j)] + " " + String.valueOf(i);
                        eft[list[i].get(j)-1] += timeArray[i-1];
                }
            }
            writer.print(predArray[i] + " \t");
        }

        writer.print("\nSuccessors\t");
        for (int i = 0; i < size; i++) {
            if (list[i].size() > 0) {
                for (int j = 0; j < list[i].size(); j++) {
                    writer.print(list[i].get(j) + " ");
                }
            }
            writer.print("\t");
        }
        writer.print("\nTime/Task\t\t");
        for (int t = 0; t < size - 1; t++) {
            writer.print(timeArray[t] + "\t\t");
        }

        writer.print("\nEFT\t\t\t\t");//precessors
        for(int i=0; i<timeArray.length-1; i++){
            writer.print(eft[i]+"   \t");
        }

        writer.print("\nLFT\t\t");//successors

            writer.print("\nCritical Tasks\t\t");
        }

}






