import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;
import java.lang.*;

public class BuildSchedule {

    int numTimeSlots;
    int numRooms;
    int numCourses;

    //2d array to represent the schedule
    Course[][] schedule;
    //int[] lastClassForTime;
    HashMap<String, int[]> nextRoomForTime;
    HashMap<String, int[]> lastClassForTime;
    HashMap<String, boolean[]> openTimes;

    public BuildSchedule(int timeSlots, int rooms, int courses, HashMap<String, Department> departments){
        this.numTimeSlots=timeSlots;
        this.numRooms=rooms;
        this.numCourses=courses;
        this.schedule=new Course[numTimeSlots][numRooms];
        this.lastClassForTime = new HashMap<String, int[]>(departments.size());
        this.openTimes = new HashMap<String, boolean[]>(departments.size());
        this.nextRoomForTime = new HashMap<String, int[]>(departments.size());
        /*
        public void iterateUsingForEachIterator(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            tring value = entry.getValue();
            System.out.println("Key=" + key + ", Value=" + value);
            }   
        }
        */
        Iterator<Map.Entry<String,Department>> iterator = departments.entrySet().iterator();
        while(iterator.hasNext()){
            boolean[] timess = new boolean[numTimeSlots];
            int[] lastClassForTime3 = new int[numTimeSlots];
            int[] nextRoomForTime3 = new int[numTimeSlots];
            for(int i=0; i<numTimeSlots; i++){
                timess[i] = true;
                nextRoomForTime3[i] = 0;
                lastClassForTime3[i] = 0;
            }
            Map.Entry<String, Department> entry = iterator.next();
            String putting = entry.getKey();
            //String putting = dep.next().key();
            openTimes.put(putting, timess);
            nextRoomForTime.put(putting, nextRoomForTime3);
            lastClassForTime.put(putting,lastClassForTime3);
        }
        
        
    }

    public Course[][] getSchedule() {
        return this.schedule;
    }

    //This gets the last time slot used at each room
    public int getLastClass(String dep1, int time){
        int[] classes = this.lastClassForTime.get(dep1);
        return classes[time];
    }

    public int getNextRoom(String dep1, int time){
        int[] classes = this.nextRoomForTime.get(dep1);
        return classes[time];
    }

    public boolean[] openTimeSlots(String dep1){
        return this.openTimes.get(dep1);
    }

    //assign a course to a specific time slot and room 
    public void assignCourse(int timeSlot, Room room, Course course){
        if (timeSlot>=0 && timeSlot<numTimeSlots && room.getIndex()>=0 && room.getIndex()<=numRooms) {
            schedule[timeSlot][room.getIndex()] = course;
            int[] temp = lastClassForTime.get(course.getDepartment().getName());
            temp[timeSlot] = course.getIndex();
            if(room.getIndex()==numRooms){
                boolean[] temp2 = openTimes.get(course.getDepartment().getName());
                temp2[timeSlot] = false;
            }
            else{
                int[] temp3 = nextRoomForTime.get(course.getDepartment().getName());
                temp3[timeSlot]++;
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
                    if (courses.get(k).getRoom().getIndex() == j+1) {index = k; break;}
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
