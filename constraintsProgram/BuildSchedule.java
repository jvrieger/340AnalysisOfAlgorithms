import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;

public class BuildSchedule {

    int numTimeSlots;
    int numRooms;
    int numCourses;

    //2d array to represent the schedule
    Course[][] schedule;
    int[] lastClassForTime;
    int[] nextRoomForTime;
    boolean[] openTimes;

    public BuildSchedule(int timeSlots, int rooms, int courses){
        this.numTimeSlots=timeSlots;
        this.numRooms=rooms;
        this.numCourses=courses;
        this.schedule=new Course[numTimeSlots][numRooms];
        this.lastClassForTime = new int[numRooms];
        this.openTimes = new boolean[timeSlots];
        this.nextRoomForTime = new int[timeSlots];
        for(int i=0; i<timeSlots; i++){
            this.openTimes[i] = true;
            this.nextRoomForTime[i] = 0;
        }
        
    }

    public Course[][] getSchedule() {
        return this.schedule;
    }

    //This gets the last time slot used at each room
    public int getLastClass(int timeSlot){
       return this.lastClassForTime[timeSlot];
    }

    public int getNextRoom(int timeSlot){
        return this.nextRoomForTime[timeSlot];
    }

    public boolean[] openTimeSlots(){
        return this.openTimes;
    }

    //assign a course to a specific time slot and room 
    public void assignCourse(int timeSlot, Room room, Course course){
        if(timeSlot>=0 && timeSlot<numTimeSlots && room.getId()>0 && room.getId()<=numRooms) {
            schedule[timeSlot][room.getId()-1] = course;
            lastClassForTime[timeSlot] = course.getId()-1;
            if(room.getId()==numRooms){
                openTimes[timeSlot]=false;
            }
            else{
                this.nextRoomForTime[timeSlot]++;
            }
        }
        else {
            System.out.println("Timeslot or room id not in bounds of schedule.");
        }
    }

    //print the schedule
    public void printSchedule(){
        for (int timeSlot=0; timeSlot<numTimeSlots; timeSlot++) {
            System.out.println("\nTime: " + (timeSlot+1));
            for(int room=0; room<numRooms; room++) {
                Course course = schedule[timeSlot][room];
                if(course!=null) {
                    System.out.println("Room " + (room + 1) + ": " + course);
                }
                else {
                    System.out.println("Room " + (room+1) + ": Empty");
                }
            }
        }
        System.out.println();
    }

    public void reassignRooms() {
        for (int i = 0; i < schedule.length; i++) { //for each timeslot
            ArrayList<Course> courses = new ArrayList<>();
            for (int p = 0; p < schedule[i].length; p++) {if (schedule[i][p] != null) {courses.add(schedule[i][p]); schedule[i][p] = null;}}
            for (int j = 0; j < schedule[i].length; j++) {
                int index = -1;
                for (int k = 0; k < courses.size(); k++) {
                    if (courses.get(k).getRoom().getId() == j+1) {index = k; break;}
                }
                if (index > -1) {
                    schedule[i][j] = courses.get(index);
                }             
            }
        }
    }

    public Course[] getCourses(int timeslot) {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < schedule[timeslot].length; i++) {
            if (schedule[timeslot][i] != null) {
                courses.add(schedule[timeslot][i]);
            } 
        }
        int n = courses.size();
        Course[] coursesArr = new Course[n];
        for(int j = 0; j < n; j++) {
            coursesArr[j] = courses.get(j);
        }

        return coursesArr;
    }

}
