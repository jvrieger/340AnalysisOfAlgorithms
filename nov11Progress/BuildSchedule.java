public class BuildSchedule {

    int numTimeSlots;
    int numRooms;
    int numCourses;

    //2d array to represent the schedule
    Course[][] schedule;

    public BuildSchedule(int timeSlots, int rooms, int courses){
        this.numTimeSlots=timeSlots;
        this.numRooms=rooms;
        this.numCourses=courses;
        this.schedule=new Course[numTimeSlots][numRooms];
    }

    //assign a course to a specific time slot and room 
    public void assignCourse(int timeSlot, Room room, Course course){
        if(timeSlot>0 && timeSlot<=numTimeSlots && room.getId()>0 && room.getId()<=numRooms) {
            schedule[timeSlot-1][room.getId()-1]=course;
        }
        else {
            System.out.println("Timeslot or room id not in bounds of schedule.");
        }
    }

    //print the schedule
    public void printSchedule(){
        for(int room=0; room<numRooms; room++){
            System.out.println("\nRoom: " + room);
            for(int timeSlot=0; timeSlot<numTimeSlots; timeSlot++){
                Course course= schedule[timeSlot][room];
                if(course!=null){
                    System.out.println("Time " + timeSlot + ": "+ course);
                }
                else{
                    System.out.println("Time " + timeSlot + ": Empty");
                }
            }
        }
        System.out.println();
    }

}
