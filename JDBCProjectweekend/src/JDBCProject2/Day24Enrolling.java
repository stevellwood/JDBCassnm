package JDBCProject2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


//import JDBCconnect.java;  //why not work
public class Day24Enrolling {
    Db db = null;
        public static Connection conn = null;
        public static PreparedStatement pStmt=null;
        public static ResultSet myRs=null;
        public static Statement stmt=null;
        static String first;// = Student.firstName;
        static String last; //= Student.firstName;
        static String major; //= Student.firstName;
        //int sat;
        //double gpa;
       // int major_id;
        //int id = -1;
        public static void main(String[] args) throws Exception {
            JDBCconnect2 connection1 = new JDBCconnect2();
            try {
           conn=     connection1.connecttoDB();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Day24Enrolling d24 = new Day24Enrolling();
            d24.actforStudent(Student.newstudents);
        }
        public Day24Enrolling() throws SQLException{
            Student st = new Student();//this fills the arraylist
            //this gets around the demand to make static
        }
        public boolean scheduleClasses(Student stu) {
            try {
                String sql = "select m.description as 'Major', c.Id as 'classId', "
                        //select the majorname and the class id and the classname from tables major, majclasrel
                        + " concat(c.subject,c.section) as 'Class' "
                        + " from major m "
                        + " join major_class_relationship mc "
                        + "     on m.id = mc.major_id "
                        + " join class c " 
                        + "     on c.id = mc.class_id where m.id = ?";
                //this returns the required classes given a particular major
                pStmt = conn.prepareStatement(sql);
                //pStmt.setInt(1, stu.getMajor_id());
                pStmt.setInt(1, 1);
                System.out.println(stu.getMajor_id()+" is the 2ndtrymajorid for "+stu.getFullName());  
                  myRs = pStmt.executeQuery();
               //his method  ResultSet rs = db.getSqlResultSet(sql, this.id);
                //run query and store in rs table
                int idx = 0;
                ArrayList<String> classIds = new ArrayList<String>();//put the rs into arraylist
                System.out.println("classIds ="+classIds);
                while(myRs.next()) { // should be multiple
                    int classId = myRs.getInt("classId");
                    classIds.add(String.valueOf(classId)); //for whatever reason classids is string arrayl
                    idx++;
                    System.out.println("fffffffffffffffff");
                    System.out.println("Major id's adding is "+String.valueOf(classId));
                    if(idx == 2)//only pick 2 form the major
                        break;
                }
                
                // get other classes to total four
            //    sql = "SELECT id from class where id not in (" + String.join(",", classIds) + ")";
                sql = "SELECT id from class where id not in (" + String.join(",", classIds) + ")";
                //String.join(",", classIds) this puts all the elements of classIds into a long list separated by ,
                System.out.println("Checking"+sql);
                if(conn!=null) System.out.println("connection working");
                else if (conn==null) System.out.println("no connection in classid");
                //System.out.println("Error MyRs" + myRs.getString(1));
              //  myRs = db.getSqlResultSet(sql);
                pStmt = conn.prepareStatement(sql);
                
         myRs=   pStmt.executeQuery(sql);
//                System.out.println(pStmt);
//                myRs = pStmt.executeQuery();
                int idx2 = 0;
                while(myRs.next()) { // should be multiple
                    int classId = myRs.getInt(1);
                    classIds.add(String.valueOf(classId));
                    idx2++;
                    if(idx2 == 4)
                        break;
                }
                
                // enroll in all the selected classes
                sql = "INSERT student_class_relationship (student_id, class_id) values (?,?)";
                pStmt = conn.prepareStatement(sql);
               //pStmt.setInt(1, 1);
                //myRs = pStmt.executeQuery();
                pStmt.setInt(1, stu.getId());
                for(String classId : classIds) {
                    pStmt.setInt(2, Integer.parseInt(classId));
                    int recsAffected = pStmt.executeUpdate();
                    System.out.println("Class was scheduled for student: "+classId);
                    if(recsAffected != 1) {
                        throw new Exception("Class was not scheduled for Student!");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        
       
        public void actforStudent(ArrayList<Student> newstudents) throws Exception{
            for (Student stu: Student.newstudents){
//                first = stu.firstName;
//                last= stu.lastName;
//                sat = stu.sat;
//                gpa = stu.gpa;
                //major = stu.major;
                System.out.println("In main: " +stu.firstName+" "+stu.lastName+" "+stu.sat+" "+stu.gpa+" "+stu.major);
                String fromenroll =(enroll(stu));
                System.out.println(fromenroll);
                scheduleClasses(stu);
                
        }}
        public String enroll(Student stud) throws Exception {
            //String sql0 ="insert student value (?,?,?,?,?,?);";
            System.out.println("In enroll: " +stud.firstName+" "+stud.lastName+" "+stud.sat+" "+stud.gpa+" "+stud.major);
            if(conn!=null) System.out.println("connection working");
            else if (conn==null) System.out.println("no connection");
            //pStmt= conn.prepareStatement("insert student value (?,?,?,?,?,?);");
            String sql2  = "insert into student (first_name, last_name, sat, gpa, major_id) values (?, ?, ?, ?, ?);";
            pStmt= conn.prepareStatement(sql2);
              // pStmt.setNull(1, java.sql.Types.INTEGER);
                pStmt.setString(1,stud.firstName);
                pStmt.setString(2, stud.lastName);//first
          
                pStmt.setInt(3, stud.sat);//sat
                pStmt.setDouble(4, stud.gpa);//gpa
               // pStmt.setString(5, stud.major);
//                pStmt.executeUpdate();
                //pStmt.updateQuery();       
              
            StringBuffer sbf = new StringBuffer();
            sbf.append("Enrolling " + stud.getFullName() + " as a new student.");
            sbf.append(stud.getFullName() + " has an SAT score of " + stud.sat + ".\n");
    //this        
            int major_id = evalMajor(stud.major,stud.sat);//returns major id if valid and -2 if not valid
              
            stud.setMajor_id(major_id);
            System.out.println(stud.getMajor_id()+" is the majorid for "+stud.getFullName());  
            if(stud.getMajor_id()  == -1 || stud.getMajor_id()  == -2) {//problems
                pStmt.setNull(5, java.sql.Types.INTEGER);
                if(stud.getMajor_id() == -1) {
                    sbf.append("The major selected by " + stud.getFullName() + " was not found.\n");
                } else {
                    sbf.append("Sorry, but a " + stud.major + " major requires an SAT score of ")
                      .append(getMinSatScore(-1, stud.major ) + ".\n");
                }
            } else {//major is ok
                pStmt.setInt(5,  stud.getMajor_id());//we got this from evalMajor
                sbf.append("Assigned " + stud.getFullName() + " to the " + stud.major + " major ")
                  .append("which requires an SAT score of " + getMinSatScore(major_id, "") + ".\n");
            }
            pStmt.executeUpdate();
           //MyRs can be used over and over because it gets erased whenever reassign it
            //setStudId(stud);
         // get the new id from autoincrement
            String sql = "SELECT LAST_INSERT_ID()";
            
            stmt = conn.createStatement();
            if(conn!=null) System.out.println("connection working2");
            else if (conn==null) System.out.println("no connection2");
            myRs = stmt.executeQuery(sql);
            if(!myRs.next())
                throw new Exception("Cannot get the LAST_INSERT_ID()");
            //stud.id = myRs.getInt(1);//for local
            stud.setId(myRs.getInt(1));//this might be unneeded but assigning it in STudent as well
            //student.setId(rs.getInt("id")); didn't work threw null unless we use the appropriate method
            System.out.println("Set student id in enroll to "+myRs.getInt(1));
            return sbf.toString();
        }
        
        private int evalMajor(String major, int sat) throws SQLException {
            int major_id = -1; //default is unknown
            PreparedStatement pmajor= conn.prepareStatement("select id, req_sat from major where description = ?;");
            pmajor.setString(1, major);
            ResultSet rs = pmajor.executeQuery();

          //was there a major declared?
            if(rs.next()) {
                // see if sat is compatible
                if(rs.getInt("req_sat") > sat) {//this gets param from the actual table
                    major_id = -2;
                } 
                // if we passed, the set the id
                else {          
                    major_id = rs.getInt("id");//1234567
                }
            }
            pmajor.close();//close prepared statement
            
            return major_id;
        }
        
        private int getMinSatScore(int id, String description) throws SQLException {
            PreparedStatement pminSat = conn.prepareStatement("select req_sat from major where id = ? or description = ?;");
            pminSat.setInt(1, id);
            pminSat.setString(2, description);
            ResultSet rs = pminSat.executeQuery();
            
            // If major not present, then we can at least use the stud id
            rs.next();//get next row
            int minSat = rs.getInt("req_sat");//for this case
            pminSat.close();
            return minSat;      
        }
//        private void setStudId(Student student) throws SQLException {
//            PreparedStatement pfid = conn.prepareStatement("select id from student where first_name = ? and last_name = ? and sat = ?;");
//            pfid.setString(1, student.firstName);
//            pfid.setString(2, student.lastName);
//          //  pfid.setDouble(3, student.gpa);
//            pfid.setInt(3,  student.sat);
//            
//            ResultSet rs = pfid.executeQuery();
//            rs.next();
//            student.setId(rs.getInt("id"));
//        }
        
        public static void close() throws SQLException{
            if(conn!=null)
                conn.close();
            if(pStmt != null)
                pStmt.close();
            if(myRs != null)
                myRs.close();
            
        }

    }

