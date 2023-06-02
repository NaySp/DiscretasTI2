package ui;

import exception.ExistenceVertexException;
import exception.VertexNotFoundException;
import graph.AdjacentListGraph;
import graph.AdjacentListVertex;
import model.Manager;
import model.Station;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private  Manager manager;

    public Main(){
        manager = new Manager();
    }
    public static void main(String[] args) throws ExistenceVertexException, VertexNotFoundException {
       Main m =new Main();
       Scanner sc = new Scanner(System.in);
        m.readStations();
        m.readEdges();
        int option;
        do{
            System.out.print("Choose an option : \n1.Show the best route between two stations\n2.\n0.Exit");
            option = sc.nextInt();
            switch(option){
                case 1:
                    m.bestRoute();
                    break;
                case 2:
                    break;
                case 0 :
                    break;
            }
        }while(option!=0);

    }

    public void bestRoute(){
        System.out.println("Enter the origin station");
        Scanner sc = new Scanner(System.in);
        String origin = sc.nextLine();
        System.out.println("Enter the destination station");
        String destination = sc.nextLine();
        try {
            System.out.println(manager.bestRoute(origin, destination));
        } catch (VertexNotFoundException e) {
            System.out.println("One of the stations is not in the system");
        }
    }

    public void readStations(){
        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "\\data");
        File routesInformation = new File(dataDirectory + "\\Station-info.csv");
        String filePath = routesInformation.getAbsolutePath();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String vertex = parts[0].trim();
                int number = Integer.parseInt(parts[1].trim());
                manager.addStation(vertex, number);
            }

        } catch (IOException | ExistenceVertexException e) {
            e.printStackTrace();
        }
    }

    public void readEdges(){
        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "\\data");
        File routesInformation = new File(dataDirectory + "\\stations_connections.csv");
        String filePath = routesInformation.getAbsolutePath();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String vertex =  parts[0].trim().toLowerCase();
                String dest  = parts[1].trim().toLowerCase();
                int weight = Integer.parseInt(parts[2].trim());
                manager.addEdge(vertex,dest,weight);
            }
        } catch (IOException | ExistenceVertexException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
