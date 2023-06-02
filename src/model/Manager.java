package model;

import exception.ExistenceVertexException;
import exception.VertexNotFoundException;
import graph.AdjacentListGraph;
import graph.IGraph;
import graph.Vertex;

import java.util.ArrayList;

public class Manager {
    private AdjacentListGraph<Station> stations;
    private ArrayList<Station> stationsList;

    public Manager(){
        stations = new AdjacentListGraph<>(false,false,false);
        stationsList = new ArrayList<>();
    }

    public void addStation(String name, int id) throws ExistenceVertexException {
        Station station = new Station(name, id);
        stations.addVertex(station);
        stationsList.add(station);
    }

    public String bestRoute(String origin, String destination) throws VertexNotFoundException {
        Station origin1 = searchVertex(origin);
        Station destination1 = searchVertex(destination);
        stations.dijkstra(origin1);
        return stations.getPath(destination1);
    }

    public void addEdge(String source, String dest, int weight) throws Exception {
        Station origin1 = searchVertex(source);
        Station destination1 = searchVertex(dest);
        stations.addEdge(origin1, destination1, weight);
    }

    public Station searchVertex(String value){
        for(Station station: stationsList){
            if(station.getName().equalsIgnoreCase(value)){
                return station;
            }
        }
        return null;
    }
}
