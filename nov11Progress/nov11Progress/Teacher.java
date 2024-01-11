class Teacher {

    int teacherId;
    int class1;
    int class2;

    public Teacher(int teacherId, int class1, int class2) {
        this.teacherId = teacherId;
        this.class1 = class1;
        this.class2 = class2;
    }

    public Teacher(int teacherId, int class1) {
        this.class1 = class1;
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return this.teacherId;
    }

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

    public String toString() {
        return "Teacher " + this.teacherId + " teaches " + this.class1 + " and " + this.class2;
    }
}