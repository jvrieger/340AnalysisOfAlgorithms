import java.util.*;

class Teacher {

    int teacherId;
    int teacherIndex;
    //int class1;
    //int class2;
    ArrayList<Integer> classes = new ArrayList<Integer>(); //stores Course IDs

    public Teacher(int teacherId, ArrayList<Integer> classes, int Index) {
        this.teacherId = teacherId;
        this.classes = classes;
        this.teacherIndex = Index;
    }

    public Teacher(int teacherId){
        this.teacherId = teacherId;
        this.classes = new ArrayList<Integer>();
    }

    public Teacher(int teacherId, int class1, int index) {
        this.classes = new ArrayList<Integer>();
        this.classes.add(class1);
        this.teacherId = teacherId;
        this.teacherIndex = index;
    }

    public int getTeacherId() {
        return this.teacherId;
    }

    /* 
    public int getClass1() {
        return this.class1;
    }

    public int getClass2() {
        return this.class2;
    }

    public void setClass1(int class1) {
        this.class1 = class1;
    }

    public void setClass2(int class2) {
        this.class2 = class2;
    }
    */

    public void setClasses(ArrayList<Integer> classes){
        this.classes = classes;
    }

    public ArrayList<Integer> getClasses(){
        return this.classes;
    }

    public void addClass(int c) {
        this.classes.add((Integer)c);
    }

    public String toString() {
        return "Teacher " + this.teacherId;
    }
}