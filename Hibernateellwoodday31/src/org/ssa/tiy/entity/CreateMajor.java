package org.ssa.tiy.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateMajor {

    public static void main(String[] args){
        SessionFactory factory = new Configuration() //Configuration
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Major.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try{
            //int id = 1;
            //creating new instance of class AND database record (not inserted yet)
            //transactions are sql things that do  multiple sql things. either all must happen or cancel
            
            Major maj = new Major("test2");
            //this works with feedback: New recordorg.ssa.tiy.entity.Major@5a18cd76 as maj
            //Student std1 = new Student("d", "e", 1600,4.0,3);
                    //begin transaction
            session.beginTransaction();
            //save is insert or update
            System.out.println("New record"+maj);//New record  0 test2                 400 once we set up toSTring  method
            session.save(maj);
            //session.save(std1);
            //
            session.getTransaction().commit();
        }catch(Exception ex) {ex.printStackTrace();}
        finally{
            factory.close();
        }
    }

}
