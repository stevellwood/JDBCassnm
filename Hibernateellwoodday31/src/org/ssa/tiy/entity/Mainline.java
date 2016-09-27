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
        uml.createaMajor("Basket Weaving");
        uml.deleteMajor("Basket Weaving");
        uml.createaMajor("Basket Weaving");
        uml.updateMajorDescription("Basket Weaving", "Advanced Basket Weaving");
        uml.displayAllMajors();
       
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

