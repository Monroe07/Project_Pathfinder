import javax.swing.*;

public class Client {
    public static void main(String args[]){
        // Force USER to accept the terms to use the software
//        int selection = JOptionPane.showConfirmDialog(null, "Confirm you understand", "Liability Disclaimer",JOptionPane.OK_OPTION, JOptionPane.OK_CANCEL_OPTION);
//        if(selection == JOptionPane.OK_OPTION){
            Model m = new Model("Airport_List_US_CA.csv");
            View v = new View("Project Pathfinder");
            Controller c= new Controller(m,v);

            c.initController();
//        }
//        // Otherwise display a error message
//        else{
//            JOptionPane.showMessageDialog(null, "You must accept the terms to use Project Pathfinder\nGoodbye!","ERROR" ,JOptionPane.ERROR_MESSAGE);
//        }


    }
}
