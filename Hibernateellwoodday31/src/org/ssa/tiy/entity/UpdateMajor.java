package org.ssa.tiy.entity;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.ssa.tiy.entity.Major;





public class UpdateMajor {
    public static void deleteMajor(int majid){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
        try{
            session.beginTransaction();
            Major major=session.get(Major.class, majid);//what is this fetching?
            session.delete(major);//do we need this?
//            session.createQuery("delete Major where id=9").executeUpdate();other way
            session.getTransaction().commit();  
        }finally{
            factory.close();
        }
    }
    public void deleteMajor(String majordes){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
        try{
            
            session.beginTransaction();
            int mid= getMID(majordes);
            Major major=session.get(Major.class, mid);//what is this fetching?
            session.delete(major);//do we need this?
//            session.createQuery("delete Major where id=9").executeUpdate();other way
            session.getTransaction().commit();  
        }finally{
            factory.close();
        }
    }
    public int getMID(String majordes){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
       
        try{
            //
            session.beginTransaction();
        Major md = (Major) session.createQuery("select id from Major where 'description'= "+ "'"+ majordes+ "'");
        int id=md.getId();
        session.getTransaction().commit();  
        return id;
        }finally{
            factory.close();
        }
        //List<Student> students= session.createQuery("from Student").list();
    }
    public void createaMajor(String majordes){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
        try{
            Major majorn1= new Major(majordes);
            session.beginTransaction();
            
            System.out.println("New record"+majorn1);
            session.save(majorn1);//do we need this?
//            session.createQuery("delete Major where id=9").executeUpdate();other way
            session.getTransaction().commit();  
        }finally{
            factory.close();
        }
    }
    public void updateMajorDescription(String majordes,String majordesc2){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
        try{
            
            session.beginTransaction();
            int majorId= getMID(majordes);
            Major major=session.get(Major.class, majorId);
            major.setDescription(majordesc2);
            //session.save(majorn1);//do we need this?
//            session.createQuery("delete Major where id=9").executeUpdate();other way
            session.getTransaction().commit();  
        }finally{
            factory.close();
        }
    }
    public void displayAllMajors(){
        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
    try{
        //
        session.beginTransaction();
        //query student
        List<Major> majors= session.createQuery("from Major").list();//??Select *?
        ///Where does this arraylist exist?
        //dispaly student
        display(majors);
        
//      students=session.createQuery("from Student s where s.firstName='Amsal'").list();
//      System.out.println("Single Record");
//      displayStudents(students);
//      
        
        session.getTransaction().commit();
        
    }catch(Exception ex){
        ex.printStackTrace();
    }finally{
        factory.close();
    }}
    private void display(List<Major> majors) {
        for(Major std: majors)
            System.out.println(std);
    }
}

