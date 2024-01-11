
class Room implements Comparable<Room> {

    int id;
    int capacity;

    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return this.id;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setId(int id) {
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