public class Course implements Comparable<Course> {

    int id;
    int prefVal;

    public Course(int id, int prefVal) {
        this.id = id;
        this.prefVal = prefVal;
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