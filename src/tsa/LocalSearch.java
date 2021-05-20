package tsa;

import java.util.ArrayList;
import java.util.Collections;

public class LocalSearch {
	
	public ArrayList<Integer> RandomSearch(GraphStructure graph,Route route) {
		Route tour = route;
		int cost=tour.getThisCost(graph);
		Route tempTour = new Route();
		tempTour=route;

		for (int i = 0; i <= 100; i++) {

			tempTour=new Route();
			tempTour.setRoute(route.getRoute());
			tempTour.generateRandomRoute();
			int tempCost=tempTour.getThisCost(graph);
			
			if(tempCost<cost) {
				cost=tempCost;
				tour.setRoute(tempTour.getRoute());
			}
			
		}
		return tour.getRoute();
	}
	
	public ArrayList<Route> Opt(GraphStructure graph,Route route){
		ArrayList<Integer> optRoute=route.getRoute();
		ArrayList<Route> neighbourhood= new ArrayList<Route>();
		for(int i=0;i<route.getRouteSize();i++) {
			  for (int j = i+1; j<route.getRouteSize(); ++j) {
				  ArrayList<Integer> newRoute=swap(optRoute,i,j);
				  route.setRoute(newRoute);
				  neighbourhood.add(route);
			  }
		}
		return neighbourhood;
	}
	public ArrayList<Integer> swap(ArrayList<Integer> optRoute, int i, int  j) {
		  int diff = Math.abs(j-i);

		  diff = (diff+1)/2;
		  for (int k = 0; k <diff; k++) {
			  Collections.swap(optRoute,(i+k),(j-k));		    
		  }
		return optRoute;
	}
	
		public Route getBestNeighbour(GraphStructure graph,ArrayList<Route> neighbourhood) {
			Route bestRoute = neighbourhood.get(0);
			int cost=neighbourhood.get(0).getThisCost(graph);
			for(Route curRoute:neighbourhood) {
				int curCost=curRoute.getThisCost(graph);
				if(curCost<cost) {
					cost=curCost;
					bestRoute=curRoute;
				}
			}
			return bestRoute;
		}
}
