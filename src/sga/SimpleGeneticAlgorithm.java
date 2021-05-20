package sga;

import tsa.GraphStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import tsa.Route;

public class SimpleGeneticAlgorithm {

	private static final double mutationRate = 0.025;
	private static final int tournamentSize = 5;
	private static final boolean elitism = true;
	private Route solution;

	public boolean runAlgorithm(int populationSize, Route solution, GraphStructure graph) {
		solution.setRandomRoute();
		this.solution = solution;


		if (solution.getThisCost(graph) == 0) {
			throw new RuntimeException("The solution needs to be of cost " + solution.getThisCost(graph));
		}
		setSolution(solution);
		Population curPop = new Population(solution, true, graph);

		int generationCount = 1;
		while (curPop.getFittest(graph).getThisCost(graph) > getMaxFitness(graph)) {
			//This is done to prevent generation to evolve to only contain one city/Node
			if(checkGenerationValid(curPop,graph)){
				break;
			}
			System.out.println(
					"Generation: " + generationCount + " Best Population: " + curPop.getFittest(graph).getRouteString()
							+ "Fitness:" + curPop.getFittest(graph).getThisCost(graph));
			curPop = evolvePopulation(curPop, graph, solution);
			generationCount++;
						
			if (generationCount > 100) {
				break;
			}
			
		}
		System.out.println("Solution found!");
		System.out.println("Generation: " + generationCount);
		System.out.println("Route: ");
		System.out.println(curPop.getFittest(graph).getRouteString());
		System.out.println("Fitness: ");
		System.out.println(curPop.getFittest(graph).getThisCost(graph));

		return true;
	}

	public Population evolvePopulation(Population pop, GraphStructure graph, Route route) {
		int elitismOffset;
		Population newPopulation = new Population(route, false, graph);

		if (elitism) {
			newPopulation.addIndividualAt(0, pop.getFittest(graph));
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}

		for (int i = elitismOffset; i < pop.getIndividuals().size(); i++) {
			Route indiv1 = tournamentSelection(pop, graph, route);
			Route indiv2 = tournamentSelection(pop, graph, route);
			Route newIndiv = crossover(indiv1, indiv2);
			newPopulation.getIndividuals().add(i, newIndiv);
		}

		for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	public Route crossover(Route indiv1, Route indiv2) {
		Route newSol = new Route();
		int cross = ThreadLocalRandom.current().nextInt(0, indiv1.getRouteSize());
			for (int i=0;i<cross;i++) {
				
				int city1 = indiv1.getCity(i);
				newSol.insertCityAtIndex(i, city1);
			} 
			for(int j=cross;j<indiv1.getRouteSize();j++) {
				int city2 = indiv2.getCity(j);
				newSol.insertCityAtIndex(j, city2);
			}
		
		return newSol;
	}
	private Boolean checkGenerationValid(Population pop,GraphStructure graph) {
		ArrayList<Integer> check=pop.getFittest(graph).getRoute();
		int first=check.get(0);
		int j=0;
		for(int i=1;i<check.size();i++) {
			if(first==check.get(i)) {
				j++;
			}
		}
		if(j>2) {
			return true;
		}
		return false;
	}
	private void mutate(Route indiv) {
		for (int i = 0; i < indiv.getRouteSize(); i++) {
			for (int j = i + 1; j < indiv.getRouteSize(); ++j) {
				if (Math.random() <= mutationRate) {
					ArrayList<Integer> newRoute = swap(indiv.getRoute(), i, j);
					indiv.setRoute(newRoute);
				}
			}
		}
	}

	private ArrayList<Integer> swap(ArrayList<Integer> optRoute, int i, int j) {
		int diff = Math.abs(j - i);

		diff = (diff + 1) / 2;
		for (int k = 0; k < diff; k++) {
			Collections.swap(optRoute, (i + k), (j - k));
		}
		return optRoute;
	}

	private Route tournamentSelection(Population pop, GraphStructure graph, Route route) {
		Population tournament = new Population(route, false, graph);
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.getIndividuals().size());
			tournament.getIndividuals().add(i, pop.getIndividual(randomId));
		}
		Route fittest = tournament.getFittest(graph);
		return fittest;
	}

	protected int getMaxFitness(GraphStructure graph) {
		int maxFitness = solution.getThisCost(graph);
		return maxFitness;
	}

	protected void setSolution(Route newSolution) {
		solution.setRoute(newSolution.getRoute());
	}

}