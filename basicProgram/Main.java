import java.util.*;
import java.io.*;

public class Main {

    public static final int INFINITY = 10000;
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

            /* //print out conflict matrix (matrix of Courses that have n conflicts), conflicting courses get different times
            System.out.println("Conflict Matrix: ");
            for(int i = 0; i < conflicts.length; i++) {
                for (int j = 0; j < conflicts[i].length; j++) {
                    System.out.print(conflicts[i][j] + " ");
                }
                System.out.println();
            } */

            /* //print out sorted preference list, courses that more students want = higher preference = get bigger rooms
            Arrays.sort(allCourses, Collections.reverseOrder());
            System.out.println("\nPreference List (Sorted): ");
            for(int i = 0; i < allCourses.length; i++) {
                System.out.println(allCourses[i]);
            } */

            BuildSchedule schedule = new BuildSchedule(numTimes, numRooms, numCourses); //methods: assignCourse(int time, Room, Course), printSchedule()

            //work thru bottom diagonal of conflict matrix |_\
            //working from highest to lowest conflict values, place courses of id i and j in diff timeslots
            boolean done = false;
            boolean[] coursePlaced = new boolean[numCourses];
            //int size = 0;
            int temp = 0;
            while(!done) { //done when all courses are placed in conflict-pref timeslots and arbitrary rooms

                int diagonal = 1; //index for the diagonal, above which we can ignore values since matrix is symetrical
                int maxConflictI = 1;
                int maxConflictJ = 0;
                int count = 0; //keeps track of however many conflict vals are left to process
                for (int i = 1; i < conflicts.length; i++) { //find maxConflict at the moment
                    for (int j = 0; j < diagonal; j++) {
                        //System.out.print(conflicts[i][j] + " "); //print conflict values
                        if (conflicts[i][j] > -1) {count++;} //to check if done
                        if (conflicts[i][j] > conflicts[maxConflictI][maxConflictJ]) {maxConflictI = i; maxConflictJ = j;}
                    }
                    if (diagonal < conflicts[i].length) {diagonal++;} //deal w diagonal index
                    //System.out.println();
                }

                //assign most conflicting courses to differing time slots
                if (!coursePlaced[maxConflictI]) { //if this max-conflict-value course hasn't been placed in schedule
                    boolean[] openTimes = schedule.openTimeSlots();
                    int leastConflict = INFINITY;
                    int timeSlotLeast = 0;
                    for(int i=0; i<openTimes.length; i++){
                       if (openTimes[i]){
                            if (conflicts[schedule.getLastClass(i)][maxConflictI]<leastConflict){
                                leastConflict = conflicts[schedule.getLastClass(i)][maxConflictI];
                                timeSlotLeast = i;
                            }
                       }
                       temp = timeSlotLeast; 
                    }
                    Room room = allRooms[schedule.getNextRoom(timeSlotLeast)];
                    schedule.assignCourse(timeSlotLeast, room, allCourses[maxConflictI]);
                    allCourses[maxConflictI].setRoom(room);
                    coursePlaced[maxConflictI] = true;
                }
                if (!coursePlaced[maxConflictJ]) {
                    boolean[] openTimes = schedule.openTimeSlots();
                    int leastConflict = INFINITY;
                    int timeSlotLeast = 0;
                    for(int i=0; i<openTimes.length; i++){
                       if (i != temp && openTimes[i]){  //cannot be tempIndex (cannot be at the same time as above)
                            if(conflicts[schedule.getLastClass(i)][maxConflictI]<leastConflict){
                                leastConflict = conflicts[schedule.getLastClass(i)][maxConflictI];
                                timeSlotLeast = i;
                            }
                        }
                    }
                    Room room = allRooms[schedule.getNextRoom(timeSlotLeast)];
                    schedule.assignCourse(timeSlotLeast, room, allCourses[maxConflictJ]);
                    allCourses[maxConflictJ].setRoom(room);
                    coursePlaced[maxConflictJ] = true; 
                }
                conflicts[maxConflictI][maxConflictJ] = -1; //mark seen

                //if all classes are placed, done = true, terminate loop
                if (count == 0) {done = true;}
                int countPlaced = 0;
                for (int k = 0; k < coursePlaced.length; k++) {if (coursePlaced[k]) {countPlaced++;}}
                if (countPlaced == numCourses) {done = true;}
            } //all courses are placed in schedule prioritizing low conflict, in random rooms

            //sort rooms and courses in each time slot, match them up (assign rooms to each course)
            for (int i = 0; i < numTimes; i++) {
                Course[] courses = schedule.getCourses(i);
                Arrays.sort(courses, Collections.reverseOrder());
                Room[] rooms = allRooms;
                Arrays.sort(rooms, Collections.reverseOrder());

                for (int j = 0; j < courses.length; j++) {
                    courses[j].setRoom(rooms[j]);
                }
            }

            schedule.reassignRooms(); //reorder/reassign courses to new rooms (computed above based off prefVal + roomSize)
            schedule.printSchedule(); //show final schedule
            Course[][] finalSchedule = schedule.getSchedule(); //give to Ramla!

        }

        catch (FileNotFoundException e) {
            System.out.println(e + " caught");
        }
        
    }
}