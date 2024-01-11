
import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {

    public static final int INFINITY = 10000;
    public static final String OUTPUT_FILE = "schedule.txt";
    public static final String CONSTRAINTS = "constraintsMedium.txt";
    public static final String STUDENTS = "studentsMedium.txt";
    public static void main(String[] args) {
        long Time_start = System.nanoTime();
        try {
            LookupScheduleInfo lookup = new LookupScheduleInfo();
            Teacher[] allTeachers = lookup.readConstraints(CONSTRAINTS);
            Course[][] stuPrefList = lookup.readStuPrefs(STUDENTS);

            int numTimes = lookup.getNumTimes();
            int numRooms = lookup.getNumRooms();
            int numCourses = lookup.getNumCourses();
            int numTeachers = lookup.getNumTeachers();
            int numStudents = lookup.getNumStudents();

            Course[] allCourses = lookup.getAllCourses();
            Room[] allRooms = lookup.getAllRooms();
            int[][] conflicts = lookup.getConflicts();

            int points = 0;

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
            for(int i = 0; i < numStudents; i++) {
                //System.out.println(allCourses[i]);
                Course[] toPrint = stuPrefList[i];
                for(int x = 0; x<toPrint.length; x++){
                    System.out.println(toPrint[x]);
                }
            }  */ 

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
                    allCourses[maxConflictI].setTime(timeSlotLeast);
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
                    allCourses[maxConflictJ].setTime(timeSlotLeast);
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

            Course[][] finalSchedule = schedule.getSchedule(); //give to Ramla!

            //shows if student is in each time slot, so we don't put the same student in the same time slot twice
            //initally all false 
            boolean[][] whereIsStu = new boolean[numStudents][numTimes];
            //how many classes each student is in, initally all 0
            int[] studentClassNum = new int[numStudents];
            for(int i = 0; i<numStudents; i++){
                for(int j = 0; j<numTimes; j++){
                    whereIsStu[i][j] = false; 
                }
            }
            for(int i = 0; i<numStudents; i++){
               studentClassNum[i] = 0; 
            }

            //goes through by ordered preferences of courses, and each student
            for(int j = 0; j<stuPrefList[0].length; j++){
                for(int i = 0; i < numStudents; i++) {
                    //if the course is open and the student isn't already in a class at this time
                    if(!whereIsStu[i][stuPrefList[i][j].getTime()] && stuPrefList[i][j].sizeofStudents()!=stuPrefList[i][j].getRoom().capacity){
                        //set the student at this time to true
                        whereIsStu[i][stuPrefList[i][j].getTime()] = true; 
                        stuPrefList[i][j].incrementPeople((Integer)i);
                        points++;
                        studentClassNum[i]++;
                    }
                }
            }

            int cap = 0; 
            for(int i=0; i<allCourses.length; i++){
                cap += allCourses[i].sizeofStudents();
            }
               
            for(int i = 0; i<numStudents; i++){
                for(int j = 0; j<numCourses; j++){
                    if(studentClassNum[i]==numTimes){
                        break;
                    }
                    if(allCourses[j].sizeofStudents()!=allCourses[j].getRoom().capacity){
                        if(!whereIsStu[i][allCourses[j].getTime()]){
                            whereIsStu[i][allCourses[j].getTime()] = true; 
                            allCourses[j].incrementPeople((Integer)i);
                            studentClassNum[i]++;  
                        }
                    }
                }
            } 

            cap = 0; 
            for(int i=0; i<allCourses.length; i++){
                cap+= allCourses[i].sizeofStudents();
            }
            for (int i = 0; i < allTeachers.length; i++) {
                allCourses[(allTeachers[i].getClass1())-1].setTeacher(allTeachers[i]);
                allCourses[(allTeachers[i].getClass2())-1].setTeacher(allTeachers[i]);
            }

            //FINAL OUTPUT

            System.out.printf("\n%10s%10s%10s%10s%10s\n", "Course", "Room", "Teacher", "Time", "Students");
            for (int i = 0; i < allCourses.length; i++) {
                System.out.printf("%10d%10d%10d%10d", allCourses[i].getId(), allCourses[i].getRoom().getId(), allCourses[i].getTeacher().getTeacherId(), allCourses[i].getTime());
                allCourses[i].studentsInClass();
                System.out.println();
            }

            System.out.println("\nStudent Preference Value: " + points);
            System.out.println("Best Case Student Value: " + numStudents*4);
            System.out.printf("Fit Percentage: %.2f%%\n", ((float)points/(float)(numStudents*4)) * 100);
            
            long nano_estimated_Time = System.nanoTime();
            System.out.println("time: " + nano_estimated_Time/.000000001);
        }

        catch (FileNotFoundException e) {
            System.out.println(e + " caught");
        }

    }
}