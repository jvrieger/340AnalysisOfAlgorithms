import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Registration {
    private Map<Integer,Integer>parent;

    public Registration(){
        parent=new HashMap<>();
    }
    public void union(int a, int b){
        int rootA=find(a);
        int rootB = find(b);

        if(rootA!=rootB){
            parent.put(rootA,rootB);
        }
    }
    public int find(int a){
        if(!parent.containsKeys(a)|| parent.get(a)==a){
            return a;
        }
        int root = find(parent.get(a));
        parent.put(a,root);
        return root; 
    }
    public void registerStudentToClass(int classId, int studentId){
        union(studentId,classId);
    }
    public void registerTeacherToClass(int teacherId, int classId){
        union(teacherId, classId);
    }
    public boolean isStudentInClass(int studentId, int classId){
        return find(studentId)== find(classId);
    }
    public boolean isTeacherInClass(int teacherId, int classId){

        return find(teacherId)== find(classId);
    }

    public void removeStudent(int classId, String student){
        int studentRoot=find(studentId);
        int classRoot=find(classId);
        if(studentRoot==classRoot){ 
            parent.remove(studentId);
        }
    }

    public List<Integer> getStudentsForClass(int classId){
        List<Integer> students = new ArrayList<>();

        int classRoot= find(classId);

        for(Map.entry<Integer,Integer> entry: parent.entrySet()){
            int student = entry.getKey();
            int studentRoot = find(student);
            if(studentRoot==classRoot){
                students.add(student);
            }
        }
        return students;
    }

    
}
