package com.example.daniel.university_of_lincoln_companion;

/**
 * Created by Daniel on 23/01/2016.
 *
 * SINGLETON CLASS CREATED TO HOLD THE CURRENT USRS DETAILS, INSTANCES OF CLASS HELD WITHIN EACH OF THE APPLICATIONS ACTIVITIES
 */
public class ActiveStudent {

    //region GLOBAL VARIABLES
    private static final ActiveStudent asInstance = new ActiveStudent();
    private String strStudentNumber = "n/a", strCourse = "n/a", strYear = "n/a", strFirstName = "n/a", strLastName = "n/a", strName = "n/a";
    //endregion

    private ActiveStudent(){}

    public static ActiveStudent getInstance(){ return asInstance;}

    //region SET METHODS
    public void setStudentNumber(String strStudentNumber){this.strStudentNumber = strStudentNumber;}
    public void setCourse(String strCourse){this.strCourse = strCourse;}
    public void setYear(String strYear){this.strYear = strYear;}
    public void setFirstName(String strFirstName){this.strFirstName = strFirstName;}
    public void setLastName(String strLastName){this.strLastName = strLastName;}
    public void setName(String strName){this.strName = strName;}
    //endregion

    //region GET METHODS
    public String getStudentNumber(){return strStudentNumber;}
    public String getCourse(){return strCourse;}
    public String getYear(){return strYear;}
    public String getFirstName(){return strFirstName;}
    public String getLastName(){return strLastName;}
    public String getName(){return strName;}
    //endregion

    public void logOut(){

        //region SET USER DETAILS TO DEFAULT VALUES
        strStudentNumber = "n/a";
        strCourse = "n/a";
        strYear = "n/a";
        strFirstName = "n/a";
        strLastName = "n/a";
        strName = "n/a";
        //endregion
    }

}
