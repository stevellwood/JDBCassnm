package org.ssa.tiy.entity;

public class Mainline {
    static UpdateMajor uml;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        Mainline ml = new Mainline();
        uml = new UpdateMajor();
       ml.run();
       
    }
    public void run(){
        uml.deleteMajor("testing");
        uml.deleteMajor("Basketball");
        uml.createaMajor("general");
        uml.updateMajorDescription("general", "Basketball");
        
        uml.createaMajor("American Ninja Chess");
        uml.displayAllMajors();
        uml.deleteMajor("American Ninja Chess");
        uml.deleteMajor("Basketball");
        //uml.deleteMajor("Basketball");
       
//            InsertMajor("American Ninja Chess");
//            InsertMajor("Renaissance Art of the Circle");
//            InsertMajor("Don't Delete Me");
//
//            UpdateMajorDescription("Basket Weaving", "Advanced Basket Weaving");
//
//            DeleteMajor("Don't Delete Me");
//
//            DisplayAllMajors();
        }
    }

