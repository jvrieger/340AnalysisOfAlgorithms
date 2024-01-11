/* Name: Julia Rieger 
* File: LookupScheduleInfo.java 
* Desc: 
* 
* Posesses methods to read file, manipulate data, create
* data structures and populate them, and sort through data
* 
*/

import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;

class LookupScheduleInfo {

    int numTimes; //number of timeslots from constraints file
    int numRooms; //number of rooms from constraints file
    int numCourses; //number of courses from constraints file
    int numTeachers; //number of teachers from constraints file
    int numStudents; //number of students from stupref file

    Course[][] stuPrefList; //list of student-preferred courses (index = student numbers), UPDATED AND RETURNED by readStuPrefs
    Course[] allCourses; //list of all courses that have been created, indexed by courseId. UPDATED in parseCourses
    Room[] allRooms; //list of all rooms--have roomId and roomCapacity info. UPDATED in readConstraints
    Teacher[] allTeachers; //list of all teachers-- have teacher assignment info, UPDATED AND RETURNED by readConstraints
    int[][] conflicts;
    int[] allStuId; //store stu Ids in order from file
    int stuIdCount = 0; //keep track of stuId Index
    int courseIdCount = 0; //keep track of new course Index

    /** Parses first line in constraints file to find num times
     * @param line One line from the file
     * @return int number of time slots in schedule
     */
    public static int parseNumTimes(String line) {
        //line is of the format "Class Times\t#"
	    String[] tokens = line.split("\t", 2);
        return Integer.parseInt(tokens[1]);
    }

    /** Parses second line in constraints file to find num rooms
     * @param line One line from the file
     * @return int number of rooms in schedule
     */
    public static int parseNumRooms(String line) {
        //line is of the format "Rooms\t#"
	    String[] tokens = line.split("\t", 2);
        return Integer.parseInt(tokens[1]);
    }

    public static final int IDIDX = 0; //index of room id info
    public static final int CAPIDX = 1; //index of room capacity info 

    /** Parses one line of input by creating a Room for the given info
     * @param line One line from the constraints file
     * @return A Room to be added to allRooms
     */
    public static Room parseRoom(String line) {
        //line is of the format "roomId\troomCapacity"
    
        //split relevant section into array of course ids
        String[] tokens = line.split("\t", 2);

        //return Room object
        return new Room(Integer.parseInt(tokens[IDIDX]), Integer.parseInt(tokens[CAPIDX]));
    }

    /** Parses next line in constraints file to find num courses
     * @param line One line from the file
     * @return int number of courses in schedule
     */
    public static int parseNumCourses(String line) {
        //line is of the format "Classes\t#"
	    String[] tokens = line.split("\t", 2);
        return Integer.parseInt(tokens[1]);
    }

    /** Parses next line in constraints file to find num teachers
     * @param line One line from the file
     * @return int number of teachers in schedule
     */
    public static int parseNumTeachers(String line) {
        //line is of the format "Teachers\t#"
	    String[] tokens = line.split("\t", 2);
        return Integer.parseInt(tokens[1]);
    }
    
    public static final int COURSEIDX = 0; //index of course id info
    public static final int TEACHERIDX = 1; //index of teacher id info 

    /** Parses a teacher and their class, adds to allTeachers arr
     * @param line One line from the constraints file
     */
    public static void parseTeacher(String line, boolean[] teacherCreated, Teacher[] allTeachers) {
        //line is of the format "CourseId\tTeacherId"
        //split line into info
        String[] tokens = line.split("\t");
        int courseId = Integer.parseInt(tokens[0]);
        int teacherId = Integer.parseInt(tokens[1]);

        //allTeachers[teacherId-1].addClass(courseId);

        
        /*
        if (teacherCreated[teacherId-1]) {
            //if teacher already made simply update their class list
            allTeachers[teacherId-1].setClass2(courseId);
        }

        else {
            //if teacher not made, make them then set their marker to created (true) in bool arr
            allTeachers[teacherId-1] = new Teacher(teacherId, courseId);
            teacherCreated[teacherId-1] = true;
        }
        */
    }

    /** Reads constraints file, updating instance vars for counts and 2 arrays:
     * allRooms and allTeachers
     * @param filename The path of the constraints file
     * @return array of all teachers
     * data in the file.
     */
    public Teacher[] readConstraints(String filename) throws FileNotFoundException {

        //Creates new Scanner object linked to filename
	    Scanner input = new Scanner(new File(filename));

        //first line gives numTimeslots
        this.numTimes = parseNumTimes(input.nextLine());

        //second line gives numRooms
        this.numRooms = parseNumRooms(input.nextLine());
        
        //create local allRooms arr to update allRooms instance var
        Room[] allRooms = new Room[this.numRooms];

        //populate allRooms
        for (int i = 0; i < this.numRooms; i++) {
            String line = input.nextLine(); //temp string to be parsed

            allRooms[i] = parseRoom(line); //make Room out of into in line and add to allRooms
        }

        //allRooms is populated, update allRooms instance var
        this.allRooms = allRooms;

        //next line gives numCourses
        this.numCourses = parseNumCourses(input.nextLine());
        this.allCourses = new Course[numCourses];

        //initialize conflict matrix to all zero
        int[][] tempConflicts = new int[numCourses][numCourses];
        for (int j = 0; j < numCourses; j++) {
            for (int p = 0; p < numCourses; p++) {
                tempConflicts[j][p] = 0;
            }
        }
        this.conflicts = tempConflicts;

        //next line gives numTeachers
        this.numTeachers = parseNumTeachers(input.nextLine());

        //rest of lines are teacher assignments
        Teacher[] allTeachers = new Teacher[this.numTeachers]; //arr of all teachers and their assigned classes

        boolean[] teacherCreated = new boolean[this.numTeachers]; //to verify we aren't making duplicate teachers

        //update allTeachers with course/teacher info in file
        for(int i = 0; i < this.numCourses; i++) {
            String line = input.nextLine(); //temp string to be parsed

            parseTeacher(line, teacherCreated, allTeachers); //call an updater method on line for allTeachers
        }

        //for all teachers set their 2 classes' conflict values to infinity
        for (int j = 0; j < allTeachers.length; j++) {
            conflicts[allTeachers[j].class1-1][allTeachers[j].class2-1] = numStudents + 100; //set conflict value of teacher's two classes to infinity
            conflicts[allTeachers[j].class2-1][allTeachers[j].class1-1] = numStudents + 100; //set conflict value of teacher's two classes to infinity
        }

        //close Scanner
	    input.close();

        //return complete allTeachers and update allTeachers instance var
        this.allTeachers = allTeachers;
        return allTeachers;
    }

     /** Parses first line in stuprefs file to find numStudents
     * @param line One line from the file
     * @return int number of students
     */
    public int parseNumStu(String line) {
        //line is of the format "Students\t#"
	    String[] tokens = line.split("\t", 2);
        this.allStuId = new int[Integer.parseInt(tokens[1])];
        return Integer.parseInt(tokens[1]);
    }

    //returns Index given a Course's Id (for allCourses), -1 if does not exist
    public int findCourse(int id) {
        for (int i = 0; i < numCourses; i++) {
            System.out.println(this.allCourses[i]);
            if (this.allCourses[i] != null) {
                if (this.allCourses[i].getId() == id) {
                    System.out.println("FOUND COURSE of index " + i);
                    return i;
                }
            }
            
        }
        return -1; 
    }

    /** Parses a line of the stupref list
     * @param line One line from the file
     * @return array of 4 courses to be added to stupref list
     */
    public Course[] parseCourses(String line, boolean[] courseCreated) {
        //number of courses is variable from 1-5"
        //first num is index (student number)
        String[] sections = line.split("\t", 2); //split line into 2 sections, stuIDS and couseIDS (variable length)
        this.allStuId[stuIdCount] = Integer.parseInt(sections[0]);
        stuIdCount++;

        //split relevant section into array of course ids
        String[] courseTokens = sections[1].split(" ", 6); //will split into # tokens + 1: ("")
        Course[] courseList = new Course[courseTokens.length-1]; //last token is likely ""

        //next (1-5) nums are course id (increment prefVal each time see a course)
        //populate stuPrefList with courses, checking whether a course has been created or not
        for (int i = 0; i < courseList.length; i++) { 
            //for (int j = 0; j < courseCreated.length; j++) {System.out.println(j + " " + courseCreated[j]);}
            if (this.allCourses == null || findCourse(Integer.parseInt(courseTokens[i])) == -1) { //create new course
                this.allCourses[courseIdCount] = new Course(Integer.parseInt(courseTokens[i]), 1, courseIdCount); //add new Course to all courses
                System.out.println("just added " + this.allCourses[courseIdCount]);
                courseList[i] = this.allCourses[courseIdCount]; //add to temp return array courseList
                courseCreated[courseIdCount] = true; //mark course created
                courseIdCount++;
            }
            else { //course alr exists
                System.out.println("flag" + courseList[i]);
                int courseId = findCourse(Integer.parseInt(courseTokens[i]));
                this.allCourses[courseId].incrPrefVal(); //add to pref value of this course
                courseList[i] = this.allCourses[courseId]; //add this course to courseList
            }
        }

        //update conflicts matrix (indexed by courseIdCount)
        for (int i = 0; i < courseList.length; i++) {
            for (int j = 0; j < courseList.length; j++) {
                if (i != j) {
                    conflicts[courseList[i].getIndex()][courseList[j].getIndex()]++;
                }
            }
        }
        return courseList;
    }

    /** Reads a stupref file, parsing every line
     * @param filename The path/name of the stupref file
     * @return 2d array of Courses to act as the stupref list
     */
    public Course[][] readStuPrefs(String filename) throws FileNotFoundException {

            //Creates new Scanner object linked to filename
            Scanner input = new Scanner(new File(filename));

            //first line gives numStudents
            this.numStudents = parseNumStu(input.nextLine());
            
            //create stuPref arr to be returned => rows (i) = numStudents, columns (j) = 4 classes
            Course[][] stuPrefList = new Course[this.numStudents][5];
            int stuIndex = 1; //so we know what row of stuPrefList we are in
            boolean[] courseCreated = new boolean[this.numCourses];

            //populate stuPrefList
            while(input.hasNextLine()) {
                String line = input.nextLine(); //temp string to be parsed
                stuPrefList[stuIndex-1] = parseCourses(line, courseCreated); //add info in line to pref list
                stuIndex++;
            }

            //close Scanner
            input.close();

            //return complete stuPrefList and update this.stuPrefList
            this.stuPrefList = stuPrefList;
            return stuPrefList;
    }

    public int getNumTimes() {
        return this.numTimes;
    }

    public int getNumRooms() {
        return this.numRooms;
    }

    public int getNumCourses() {
        return this.numCourses;
    }

    public int getNumTeachers() {
        return this.numTeachers;
    }

    public int getNumStudents() {
        return this.numStudents;
    }

    public Course[][] getStuPrefList() {
        return this.stuPrefList;
    }

    public Course[] getAllCourses() {
        return this.allCourses;
    }

    public Room[] getAllRooms() {
        return this.allRooms;
    }

    public Teacher[] getAllTeachers() {
        return this.allTeachers;
    }

    public int[][] getConflicts() {
        return this.conflicts;//prefVal
    }
}