import java.util.*;

class Department {
//mplements Comparable<Department>
    String name;
    ArrayList<Room> roomsOfDepartment = new ArrayList<Room>();
    ArrayList<Course> classesInDepartment = new ArrayList<Course>();
    //int departmentId;

    public Department(Room room, String name){
        roomsOfDepartment = new ArrayList<Room>();
        roomsOfDepartment.add(room);
        this.name = name;
        classesInDepartment = new ArrayList<Course>();
    }

    public Department(String name){
        this.name = name;
        roomsOfDepartment = new ArrayList<Room>();
        classesInDepartment = new ArrayList<Course>();
    }

    public void addRoom(Room room){
        roomsOfDepartment.add(room);
    }

    public void addCourse(Course course){
        this.classesInDepartment.add(course);
    }

    public String getName(){
        return this.name;
    }
}