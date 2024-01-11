public class Course implements Comparable<Course> {

    int id;
    int prefVal;
    int timeslot;
    Room room;

    public Course(int id, int prefVal) {
        this.id = id;
        this.prefVal = prefVal;
        this.timeslot = 0;
        this.room = null;
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
        return "Course " + this.id + ": " + this.prefVal + " ";
        //return "Course " + this.id + " has prefVal of " + this.prefVal;
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