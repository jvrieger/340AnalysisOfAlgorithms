import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            LookupScheduleInfo lookup = new LookupScheduleInfo();
            Teacher[] allTeachers = lookup.readConstraints("demo_constraints.txt");
            Course[][] stuPrefList = lookup.readStuPrefs("demo_studentprefs.txt");

            int numTimes = lookup.getNumTimes();
            int numRooms = lookup.getNumRooms();
            int numCourses = lookup.getNumCourses();
            int numTeachers = lookup.getNumTeachers();
            int numStudents = lookup.getNumStudents();

            Course[] allCourses = lookup.getAllCourses();
            Room[] allRooms = lookup.getAllRooms();
            int[][] conflicts = lookup.getConflicts();

            //print out conflict matrix (matrix of Courses that have n conflicts), conflicting courses get different times
            System.out.println("Conflict Matrix: ");
            for(int i = 0; i < conflicts.length; i++) {
                for (int j = 0; j < conflicts[i].length; j++) {
                    System.out.print(conflicts[i][j] + " ");
                }
                System.out.println();
            }

            //print out sorted preference list, courses that more students want = higher preference = get bigger rooms
            Arrays.sort(allCourses, Collections.reverseOrder());
            System.out.println("\nPreference List (Sorted): ");
            for(int i = 0; i < allCourses.length; i++) {
                    System.out.println(allCourses[i]);
            }

            BuildSchedule schedule = new BuildSchedule(numTimes, numRooms, numCourses); //methods: assignCourse(int time, Room, Course), printSchedule()
            schedule.printSchedule(); //empty schedule

            //work thru bottom diagonal of conflict matrix |_\
            //working from highest to lowest conflict values, place courses of id i and j in diff timeslots
            boolean done = false;
            boolean[] coursePlaced = new boolean[numCourses];
            while(!done) {
                int diagonal = 1; //index for the diagonal, above which we can ignore values since matrix is symetrical
                int maxConflictI = 1;
                int maxConflictJ = 0;
                int count = 0; //keeps track of however many conflict vals are left to process
                for (int i = 1; i < conflicts.length; i++) {
                    for (int j = 0; j < diagonal; j++) {
                        if (conflicts[i][j] > -1) {count++;} //to check if done
                        System.out.print(conflicts[i][j] + " "); //print conflict values

                        if (conflicts[i][j] > conflicts[maxConflictI][maxConflictJ]) {
                            maxConflictI = i;
                            maxConflictJ = j;
                        }
                    }
                    if (diagonal < conflicts[i].length) {diagonal++;} //deal w diagonal index
                    System.out.println();
                }

                if (!coursePlaced[maxConflictI]) { //if this max-conflict-value course hasn't been placed in schedule
                    schedule.assignCourse() //assign to differing time slots
                    coursePlaced
                }
                if (!coursePlaced)
                if (count == 0) {done = true;}
                //if all classes are placed, done = true
            }
        }

        catch (FileNotFoundException e) {
            System.out.println(e + " caught");
        }
        
    }
}