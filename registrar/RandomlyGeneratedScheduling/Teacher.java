class Teacher {

    int teacherId;
    //int class1;
    //int class2;
    ArrayList<Integer> classes = new ArrayList<Integer>();

    public Teacher(int teacherId, ArrayList<Integer> classes) {
        this.teacherId = teacherId;
        this.classes = classes;
    }

    public Teacher(int teacherId){
        this.teacherId = teachId;
        this.classes = new ArrayList<Integer>();
    }

    public Teacher(int teacherId, int class1) {
        this.classes = new Arraylist<Integer>();
        this.classes.add(class1);
        this.teacherId = teacherId;
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

    public void getClasses(){
        return this.classes;
    }

    public void addClass(int class){
        this.classes.add((Integer)class);
    }

    public String toString() {
        return "Teacher " + this.teacherId + " teaches " + this.class1 + " and " + this.class2;
    }
}