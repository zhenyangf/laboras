package fms.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoryManagement {
    public static void manageCategories(Scanner scanner, FinanceManagementSystem fms, User user) {
        String cmdOut = "";
        while (!cmdOut.equals("exit")) {
            System.out.println("Choose an action:");
            System.out.println("addcat - add category");
            System.out.println("addsubc - add subcategory");
            System.out.println("addp - add responsible person");
            System.out.println("export- Export data");
            System.out.println("exit - quit");
            cmdOut = scanner.next();
            switch (cmdOut) {
                case "addcat":
                    addCategory(scanner,fms,user);
                    break;
                case "addsubc":
                    addSubCategory(scanner,fms,user);
                    break;
                case "addp":
                    break;
                case "export":
                    exportData(scanner,user);
                    break;
                case "removecat":
                    removeCategory(scanner,fms,user);
                    break;
                case "exit":
                    System.out.println("Done");
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private static void addSubCategory(Scanner scanner, FinanceManagementSystem fms,User user) {

    }
    private static void writeToFile(String outputvalue){
        FileWriter fos = null;
        try{
            fos = new FileWriter("rezultatai.txt");
            fos.write(outputvalue+ "\n" + "");
            System.out.println(outputvalue);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }}

    private static void exportData(Scanner scanner,User user){
        System.out.println("Pasirinkite ka norite eksportuoti:  userId,password,category");
        String cmd = "";
        cmd = scanner.next();
        switch (cmd){
            case "userID":
                writeToFile(user.userID);
                break;
            case "password:":
                writeToFile(user.password);
                break;
            case "catergory:":
                break;
        }}

    private static void addCategory(Scanner scanner, FinanceManagementSystem fms,User user) {
        System.out.println("Enter category info (for no value add --): {Name};{Description};{Parent Category};{Root category} \n");
        ArrayList<User> resUser = new ArrayList<User>();
        resUser.add(user);
        String[] values = scanner.next().split(";");
        if(!values[2].equals("--") && !values[3].equals("--")){
            for(Category c:fms.getCategories()){
                if(c.getName().equals(values[3])){
                }
            }

        }else{

            fms.getCategories().add(new Category(values[0],values[1], LocalDate.now(),LocalDate.now(),new ArrayList<>(),resUser));
        }

    }
    private static void removeCategory(Scanner scanner, FinanceManagementSystem fms, User user){
        System.out.println("Enter category name you want to delete: Name");
        String CMD = "";
        CMD = scanner.next();
        for(int i=0;i<fms.getCategories().size();i++){
            if (CMD.equals(fms.getCategories().get(i).getName())){
                fms.getCategories().remove(i);
            }
        }


    }
}
