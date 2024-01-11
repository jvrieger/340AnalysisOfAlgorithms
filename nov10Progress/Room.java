class Room {

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

    public String toString() {
        return "Room " + this.id + " has a capacity of " + this.capacity;
    }
}