package org.ssa.tiy.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.ssa.tiy.entity.Major;



public class UpdateMajor {
   //Major majo = new Major();
    public static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Major.class)
            .buildSessionFactory();

    Session session = factory.getCurrentSession();
    
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
//            sessionFactory = getHibernateTemplate().getSessionFactory();
//            Session session = sessionFactory.getCurrentSession();
//            Query query = session.createQuery("delete major where id= ...");
//            query.setParameters("param1", value1);
//            result = (Type) query.uniqueResult();
//            session.beginTransaction();
//            int mid= getMID(majordes);
//            Query query = session.createQuery("delete major where id= "+mid);
//            Major major=session.get(Major.class, mid);//what is this fetching?
//            session.delete(major);//do we need this?
            Query query = session.createQuery("from Major where description = :desc");
            query.setParameter("desc", majordes);
            //Major maj = (Major)query.list();
            List<Major> majors= query.list();
           // session.createQuery("delete "from Major where description = :desc").executeUpdate();
            for (Major maj: majors)
                session.delete(maj);
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
//          sessionFactory = getHibernateTemplate().getSessionFactory();
//          Session session = sessionFactory.getCurrentSession();
//          Query query = session.createQuery("delete major where id= ...");
//          query.setParameters("param1", value1);
//          result = (Type) query.uniqueResult();
        //Major md = (Major) session.createQuery("select id from Major where 'description'= "+ "'"+ majordes+ "'");
            String hql = "from Major where description= :nDesc";
            System.out.println(hql);
            Query query = session.createQuery(hql);
            query.setParameter("nDesc", majordes);
                      
           // majo = (List<Major>) query.uniqueResult();
            Major maj = (Major) query.uniqueResult();
            int id=maj.getId();
            
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
        Major majorn1= new Major(majordes);
        try{
            
            session.beginTransaction();
            
            System.out.println("New record into <major> table: "+majorn1);
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
//        SessionFactory factory=new  Configuration().configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Major.class)
//                .buildSessionFactory();
//        Session session=factory.getCurrentSession();
    try{
        //
        session.beginTransaction();
        //query student using hibernate query method
        Query query= session.createQuery("from Major ");//??Select *?
        List<Major> majors = query.list();
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
        System.out.println("Major Id        Description       Required SAT");
        System.out.println("********************************************** ");
        for(Major maj: majors){
            System.out.printf("%2d\t", maj.getId());
        System.out.printf("%20s \t    ", maj.getDescription());
        System.out.printf("%4d%n", maj.getRequiredSAT());
    }
    }}

