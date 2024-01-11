import java.util.PriorityQueue;
import java.util.Comparator;

public class coursePriorityQueue {
    
    private PriorityQueue<Course> courseQueue;

    public coursePriorityQueue(){
        courseQueue= new PriorityQueue<>(new CourseComparator());
    }

    public void addCourse(Course course){
        courseQueue.offer(course);
    }

    public Course getNextPriorityCourse(){
        return courseQueue.poll();
    }

    private static class CourseComparator implements Comparator<Course>{
        public int compare(Course x, Course y){
                return Integer.compare(y.getPrefVal(),x.getPrefVal()); 
            }
        }
    }


