package sga;

import java.util.ArrayList;
import tsa.GraphStructure;
import tsa.Route;

public class Population {

	private ArrayList<Route> individuals;

	public Population(Route route, boolean createNew, GraphStructure graph) {
		individuals = new ArrayList<Route>();
		if (createNew) {
			createNewPopulation(route, graph);
		}
	}

	protected void addIndividual(Route route) {
		individuals.add(route);
	}
	
	protected void addIndividualAt(int index,Route route) {
		individuals.add(index, route);
	}
	
	protected Route getIndividual(int index) {
		return individuals.get(index);
	}

	public void printPop() {
		for (Route temp : individuals) {
			System.out.println(temp.getRouteString());
		}
	}

	protected Route getFittest(GraphStructure graph) {
		Route fittest = individuals.get(0);
		for (int i = 0; i < individuals.size(); i++) {
			if (fittest.getThisCost(graph) <= getIndividual(i).getThisCost(graph)) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}
	
	private void createNewPopulation(Route route, GraphStructure graph) {

		Route tour = new Route();
		tour=route;

		for (int i = 0; i <= 10; i++) {

			tour=new Route();
			tour.setRoute(route.getRoute());
			tour.generateRandomRoute();
			addIndividual(tour);
		}
	}

	protected ArrayList<Route> getIndividuals() {
		return individuals;
	}

}