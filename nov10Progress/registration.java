import java.util.*;
public class registration {
    private Map<Integer,List<String>>classRegistrations;

    public registration(){
        classRegistrations=new HashMap<>();
    }
    public void addStudent(int classId, String student){
        if(classRegistrations.containsKey(classId)){
            classRegistrations.get(classId).add(student);
        }
        else{
            List<String>students = new ArrayList<>();
            students.add(student);
            classRegistrations.put(classId,students);
        }
    }

    public void removeStudent(int classId, String student){
        List<String>students=classRegistrations.get(classId);
        if(students !=null){
            students.remove(student);
        }
    }

    public List<String> getStudents(int classId){
        return classRegistrations.get(classId);
    }
}
