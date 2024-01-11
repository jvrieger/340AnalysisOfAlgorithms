import java.util.*;
import java.io.*;
import java.lang.Number;

public class Main {
    public static void main(String[] args) {
        
        try {
            LookupScheduleInfo lookup = new LookupScheduleInfo();

            Teacher[] teachers = lookup.readConstraints("demo_constraints.txt");
            Course[][] courses = lookup.readStuPrefs("demo_studentprefs.txt");
            int[][] conflicts = lookup.getConflicts();
            for(int i = 0; i < conflicts.length; i++) {
                for (int j = 0; j < conflicts[i].length; j++) {
                    System.out.print(conflicts[i][j] + " ");
                }
                System.out.println();
            }
        }

        catch (FileNotFoundException e) {
            System.out.println(e + " caught");
        }
        
    }
}