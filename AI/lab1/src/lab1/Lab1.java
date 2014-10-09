/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab1;

/**
 *
 * @author Petricioiu
 */
public class Lab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Person person = new Person("Robert",21);
        Robot robot = new Robot("Jimmy");
        robot.start(person);
    }
    
}
