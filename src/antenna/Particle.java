package antenna;
import java.util.Arrays;
import java.util.Random;

public class Particle {

	private double[] velocity;
	private double[] position;
	private double[] bestPos;
	private double bestCost = 100;
	private int antennaeNum;
	private double aperture;
	private AntennaArray antenna;

	public Particle(int antennaeNumParam, double angleParam, AntennaArray antennaParam) {
		antennaeNum = antennaeNumParam;
		antenna = antennaParam;
		aperture = (double) antennaeNum / 2;
		double[] newPosition = generateRandomPosition();
		position = newPosition;
		velocity = generateInitialVelocity();
		bestPos = newPosition;
	}

	// Randomly generated to lie in the feasible region
	public double[] generateRandomPosition() {
		double[] newPosition = new double[antennaeNum];
		while (!antenna.is_valid(newPosition)) {
			for (int i = 0; i < antennaeNum; i++) {
				double randomPos = Math.random() * aperture;
				if (randomPos < aperture && randomPos > 0) {
					newPosition[i] = randomPos;
				}
			}
			newPosition[antennaeNum-1] = aperture;
		}
		Arrays.sort(newPosition);
		return newPosition;
	}

	public double[] generateInitialVelocity() {
		double[] initialVelocity = new double[antennaeNum];
		double[] randomPosition = generateRandomPosition();
		initialVelocity = halfVector(getVectorDifference(randomPosition, position));
		return initialVelocity;
	}

	public double[] moveForward(double[] gBestPos) {
		double[] newPosition = new double[position.length];
		for (int i = 0; i < velocity.length; i++) {
			newPosition[i] = position[i] + velocity[i];
		}
		if(antenna.is_valid(newPosition)) {
			double cost = antenna.evaluate(position);
			position = newPosition;
			if (cost < bestCost) {
				bestCost = cost;
				bestPos = newPosition;
			}
		}
		
		velocity = getNextVelocity(gBestPos);
		return position;
	}

	// Get v(t+1) to be used in moveNext()
	public double[] getNextVelocity(double[] bestPos) {
		double inertial = 1 / (2 * Math.log(2));
		double cognitive = 0.5 + Math.log(2);
		double social = cognitive;
		double[] nextVelocity = new double[antennaeNum];
		double[] r1 = generateUniformRandomVector();
		double[] r2 = generateUniformRandomVector();
		double[] bestDifference = getVectorDifference(bestPos, position);
		double[] globalBestPos = getVectorDifference(bestPos, position);
		double[] inertialVal = new double[antennaeNum];
		double[] cognitiveVal = new double[antennaeNum];
		double[] socialVal = new double[antennaeNum];
		for (int i = 0; i < antennaeNum - 1; i++) {
			inertialVal[i] = inertial * velocity[i];
			cognitiveVal[i] = cognitive * r1[i] * bestDifference[i];
			socialVal[i] = social * r2[i] * globalBestPos[i];
			nextVelocity[i] = inertialVal[i] + cognitiveVal[i] + socialVal[i];
		}
		return nextVelocity;
	}

	private double[] getVectorDifference(double[] vector1, double[] vector2) {
		double[] difference = new double[vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			difference[i] = vector1[i] - vector2[i];
		}
		return difference;
	}

	private double[] halfVector(double[] vector1) {
		double[] half = new double[vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			half[i] = vector1[i] / 2;
		}
		return half;
	}

	public double[] generateUniformRandomVector() {
		double[] randomVector = new double[antennaeNum];
		Random random = new Random();
		for (int i = 0; i < antennaeNum; i++) {
			randomVector[i] = random.nextDouble();
		}
		Arrays.sort(randomVector);
		return randomVector;
	}

	public double[] getPos() {
		return position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public double[] getPBestPos() {
		return bestPos;
	}

	public double getPBestSLL() {
		return bestCost;
	}

}
