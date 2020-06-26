/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripcostcalculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmetduser
 */
public class Server {

    public static void main(String[] args) {

        System.out.println("Server is ready");

        try (ServerSocket ss = new ServerSocket(9999)) {

            System.out.println("Server is waiting for client request");
            while (true) {
                Socket socket = ss.accept();
                System.out.println("Client connected");

                // Object input and output streams for receiving and sending the objects from the Client
                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());

                // read the Parameters class object that is sent from the client
                Parameters par = (Parameters) objectIn.readObject();

                // store the object's variables 
                double distance = par.getDistance();
                double MPG = par.getMPG();
                String type = par.getFuelType();

                double costOfFuel = 0;

                if ("octane".equalsIgnoreCase(type)) {
                    costOfFuel = (distance / (MPG * 0.219969248)) * readFuel("octane");
                    // set the object's cost value for 98-Octane
                    par.setCost(costOfFuel);
                    // send back the whole object to the client
                    objectOut.writeObject(par);
                } else if ("diesel".equalsIgnoreCase(type)) {
                    costOfFuel = (distance / (MPG * 0.219969248)) * readFuel("diesel");
                    // set the object's cost value for Diesel
                    par.setCost(costOfFuel);
                    // send back the whole object to the client
                    objectOut.writeObject(par);
                }
                // write the summary information into the text file
                csvWriting(par);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception" + ex.getMessage());
        }

    }

    // reading the fuel cost from CSV file 
    public static double readFuel(String type) {
        Double result = 0.0;
        String line = "";
        String[] list;

        double fuel = 0;
        double diesel = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/tripcostcalculator/costOfFuels.txt"));

            while ((line = reader.readLine()) != null) {
                //use comma as a seperator
                list = line.split(",");

                // parse the fuel cost values into the double type
                fuel = Double.parseDouble(list[0]);
                diesel = Double.parseDouble(list[1]);

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException e) {
            System.out.println("I/O Exception");
        }
        if ("octane".equals(type)) {
            result = fuel;
        } else if ("diesel".equals(type)) {
            result = diesel;
        }
        return result;
    }

    // The summary of the calculation writing part
    public static void csvWriting(Parameters par) {
        try (FileWriter fileWriter = new FileWriter("src/tripcostcalculator/dataBase.txt", true)) {

            fileWriter.write(par.toString());
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
