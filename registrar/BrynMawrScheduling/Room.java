
class Room implements Comparable<Room> {

    String id;
    int index;
    int capacity;
    Department department; 

    public Room(String id, int capacity, int index) {
        this.id = id;
        this.capacity = capacity;
        this.index = index;
    }

    public void addDepartment(Department department){
        this.department = department;
    }

    public int getIndex(){
        return this.index; 
    }
    
    public String getId() {
        return this.id;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void incrCapacity() {
        this.capacity++;
    }

    public String toString() {
        return "Room " + this.id + " has a capacity of " + this.capacity;
    }

    public int compareTo(Room b) {
        if (this.getCapacity() > b.getCapacity()) {
            return 1;
        }
        else if (this.getCapacity() < b.getCapacity()) {
            return -1;
        }
        else {
            return 0;
        }
}
}