package JDBCProject2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Student implements Comparable {
    public String id;
    String firstName;
    String lastName;
    int sat;
    double gpa;
    String major;
    private final static String MYID = "009999";
    public static ArrayList<Student> newstudents=null;//= new ArrayList<Student>(); 
    int count=0;
    //Student student1; //this causes null pointer exception when call toSTring because I guess this becomes null after going through the list and exiting fillArr
    public Student(){
        if(newstudents ==null){
            newstudents = new ArrayList<Student>(); //only fill it up once at first call.
            //we have to call new student to add all the parameters of St to Arrlist
            fillArr();
        }
        //newstudents = new ArrayList<Student>();//this will just make 1 instance EVERY time you call Student. You will have only 1 last value in arraylist
        //this.count = 0;//debugger showed me this resets every time make a new students
      //  fillArr();
    }
    void fillArr(){
        String[][] enrollees = {
                {"Adam", "Zapel", "1200", "3,0","Finance" }, 
                {"Graham", "Krakir",  "500", "2.5", "General Studies"},   
                {"Ella",  "Vader",   "800", "3.0", "Accounting" },  
                {"Stanley",   "Kupp",    "1350",    "3.3", "Engineering"},
                {"Lou",   "Zar", "9500",    "3.0", "Education" },
                
            };
      
        for(String[] aStudent : enrollees) {//for each array (single st) in arralist
            Student student1 = new Student();//make a new one  //astudent automically fills with the data from index of its iteration before even say new
            //student1.id = aStudent[0];//set id to what find at array[0]
            student1.firstName = aStudent[0];
            student1.lastName = aStudent[1];
            student1.sat = Integer.parseInt(aStudent[2]);
            student1.gpa = Double.parseDouble(aStudent[3]);
            student1.major = aStudent[4];
            count++;
            }
    }
    //@Override
//    public String toString() {
//        //String str= "Student =" + this.firstName + " "+ this.lastName+ " "+this.id+ " "+ this.monthsEmployed+ " "+ this.eyeColor+"";
//        String str2 =
//                String.format("%6s  %12s   %13s  %-8s   %-3d", this.id, this.firstName, this.lastName,this.eyeColor,this.monthsEmployed);
//        return str2;
//    }//format(%-6s   %-16s %-16s   %-9s   %6d\n,)student.id, studelnt.first, studentlast, student..getClass().getName()..
    //
    private  void rosterByFirstName() {
        Collections.sort(newstudents);
        this.printTop("Class Roster by First Name");
        for(Student str: newstudents){
            System.out.println(str.toString());  //.toString()
        }
    }
    @Override
    public int compareTo(Object objectst){
        Student student = (Student) objectst;
        return this.firstName.compareTo(student.firstName);
        }    
    void printNearestIds() {
        HashMap<String, Student> database = new HashMap<String, Student>();
        for(Student student : newstudents) {
            database.put(student.id, student);//put each student in the db with the id as the uniqid
            //in hashmaps, id's need to be all unique while the values can repeat
        }
        Set<String> ids = database.keySet();//create a new list of all the id's in the hashmap. Only sets can do this easily  http://stackoverflow.com/questions/12960265/retrieve-all-values-from-hashmap-keys-in-an-arraylist-java
        ArrayList<String> sortedlist = new ArrayList<String>(ids); //add the keys to an arraylist
        //sortedIds.sort(null);///use arraylist sort method   Collections.sort(newstudents);
        Collections.sort(sortedlist);//sort all the id's
        String goalId = MYID; // 267252, 306700, 343769
        String myId= null; String prevId= null; String nextId = null;
        for(int matchx = 0; matchx < sortedlist.size(); matchx++) { //do a filter for loop and break out when you find the match
            if(sortedlist.get(matchx) == goalId) {//if the value Object of that key ==mine
                myId = sortedlist.get(matchx);//then assign the list value to myID  this is an arraylist get not map get
                prevId = sortedlist.get(matchx-1);//get the value before my id
                nextId = sortedlist.get(matchx+1);//get the value after my id
                break;
            }
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^Get the Nearest ID's^^^^^^^^^^^^^^^^^^^^^^^^^");
        this.printTop("Class Roster by Map Id");
        System.out.println(database.get(myId.toString())+"     my ID");//get the entire object associated with the keyvalue we just got from the arraylist
        System.out.println(database.get(prevId.toString())+"     previous ID");
        System.out.println(database.get(nextId.toString())+"     following ID");
    }     
    void printTop(String head){
       
       String topline = head + " \n"
                   + "EmplID  First Name     Last Name   Eye Color   Months Employed \n"
                   + "=====   ==========     =========   =========   ===============";
       System.out.println(topline);
    }    
    public Integer retInteger(String s){
        
        if (s.matches("\\d+")){//optional minus and at least one digit"^\\d+$"// \\means?
            return Integer.parseInt(s);
        } else {System.out.println("Bad input try again");
            return -1; 
        }
    }
    void printClassRoster(){
        rosterByFirstName();
        printNearestIds();
    }
//    public static void main(String[] args) {
//        Student st = new Student();
//        
//       st.printClassRoster();
//    }
   
}
//In part1 of the assignment, you'll create a Student class. The class has the following data items which should be named as spelled as highlighted:
//
//id (this is the student's employee id at the SSA)
//firstName
//lastName
//eyeColor ("blue", "brown", or "other")
//monthsEmployed (number of MONTHS at the SSA)
//I know you'll want to create getters and setters for these, but for this assignment, since the class is not the focus on this assignment, you have my permission to make the access modifier for these data times public.

