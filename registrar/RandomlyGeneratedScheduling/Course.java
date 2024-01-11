
import java.util.*;
import java.io.*;

public class Course implements Comparable<Course> {

    int index;
    int id;
    int prefVal;
    int timeslot;
    Room room;
    Teacher teacher;
    ArrayList<Integer> stuInClass;

    public Course(int id, int prefVal, int index) {
        this.id = id;
        this.prefVal = prefVal;
        this.index = index;
        this.timeslot = 0;
        this.room = null;
        this.stuInClass = new ArrayList<Integer>(1);
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    public void setTeacher(Teacher t) {
        this.teacher = t;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void incrementPeople(int stuId){
       stuInClass.add((Integer)stuId);
    }

    public int sizeofStudents(){
        return stuInClass.size();
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setTime(int time) {
        this.timeslot = time;
    }

    public int getTime() {
        return this.timeslot;
    }

    public int getId() {
        return this.id;
    }

    public int getPrefVal() {
        return this.prefVal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrefVal(int prefVal) {
        this.prefVal = prefVal;
    }

    public void incrPrefVal() {
        this.prefVal++;
    }

    public String toString() {
        return "Course id: " + this.id + " index: " + this.index + " " + " prefVal: " + this.prefVal;
        //return "Course " + this.id + " has the students: " + this.prefVal;
    }

    public String studentsInClass(){
        String str = String.format("\t");
        for(int i = 0; i<stuInClass.size(); i++){
            str += String.format("%d ", stuInClass.get(i));
            if (i%30==0 && i!=0) {str += String.format("\n\t\t\t\t\t\t");}
        }
        return str;
    }

    public int compareTo(Course b) {
            if (this.getPrefVal() > b.getPrefVal()) {
                return 1;
            }
            else if (this.getPrefVal() < b.getPrefVal()) {
                return -1;
            }
            else {
                return 0;
            }
    }
}