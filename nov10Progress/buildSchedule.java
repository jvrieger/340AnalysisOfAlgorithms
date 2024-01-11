public class buildSchedule {
    int numTimeSlots;
    int numRooms;
    int numCourses;

    //2d array to represent the schedule
    Course[][] schedule;

    public void buildchedule(int timeSlots, int rooms, int courses){
        this.numTimeSlots=timeSlots;
        this.numRooms=rooms;
        this.numCourses=courses;
        this.schedule=new Course[numTimeSlots][numRooms];
    }

    //assign a course to a specific time slot and room 
    public void assignCourse(int timeSlot, int room, Course course){
        if(timeSlot>=0 && timeSlot<numTimeSlots && room<numRooms){
            schedule[timeSlot][room]=course;
        }
    }

    //print the schedule
    public void printSchedule(){
        for(int room=0; room<numRooms; room++){
            System.out.println("Room: " + room);
            for(int timeSlot=0; timeSlot<numTimeSlots; timeSlot++){
                Course course= schedule[timeSlot][room];
                if(course!=null){
                    System.out.println("Time Slot " + timeSlot + ": "+ course);
                }
                else{
                    System.out.println("Time Slot " + timeSlot + ": Empty");
                }
            }
        }
        System.out.println();
    }

}
