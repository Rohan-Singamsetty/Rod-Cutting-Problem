package tsa;

import java.util.ArrayList;
import java.util.Collections;

import tsa.GraphStructure;

public class Route {
	
	ArrayList<Integer> route = new ArrayList<Integer>();
	
	
	public void insertCity(int city) {
		route.add(city);
	}
	
	public void insertCityAtIndex(int index,int city) {
		route.add(index, city);
	}
	
	public void removeCity(int city) {
		route.remove(city);
	}
	
	public Integer getCity(int city) {
		return route.get(city);
	}
	public String getRouteString() {
		return route.toString();
	}
	
	public void setRoute(ArrayList<Integer> newRoute) {
		route=new ArrayList<Integer>(newRoute);
	}
	
	public ArrayList<Integer> getRoute(){
		return route;
	}
	public int getRouteSize() {
		return route.size();
	}
	
	public ArrayList<Integer> generateRandomRoute() {
		ArrayList<Integer> randomRoute = getRoute();
		Collections.shuffle(randomRoute);
		return randomRoute;
	}
	
	public void setRandomRoute() {
		ArrayList<Integer> randomRoute = getRoute();
		Collections.shuffle(randomRoute);
		setRoute(randomRoute);
	}
	
	public int getThisCost(GraphStructure graph) {
		int cost=0;
		for(int i=0; i<(route.size()-1);i++) {
			cost+=graph.getWeight(route.get(i), route.get(i+1));
		}
		return cost;
	}
	
    public boolean containsCity(int city){
        return route.contains(city);
    }
}
