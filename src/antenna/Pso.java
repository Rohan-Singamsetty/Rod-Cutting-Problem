package antenna;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class Pso {

	private int numAntenna;
	private double angle;
	private double aperture;
	private AntennaArray antenna;

	public Pso(int numAntenna, double angle) {
		this.numAntenna = numAntenna;
		this.angle = angle;
		this.antenna = new AntennaArray(numAntenna, angle);
		this.aperture = (double) numAntenna / 2;

		randomSSL(10);

		double[] swarm = particleSwarm(5);

		System.out.println(antenna.evaluate(swarm));
		System.out.println(Arrays.toString(swarm));

	}

	public static void main(String[] args) {
		new Pso(3, 90);
	}

	private void randomSSL(int time) {

		double[] sslDesign = generatePositions();
		double ssl = antenna.evaluate(sslDesign);

		for (int i = 0; i < time; i++) {

			double[] design = generatePositions();

			if (antenna.is_valid(design)) {
				double check = antenna.evaluate(design);
				if (check < ssl) {
					ssl = check;
					sslDesign = design;
				}
			}
		}
		System.out.println(ssl);
		System.out.println(Arrays.toString(sslDesign));
	}

	private double[] generatePositions() {
		double[] design = new double[numAntenna];

		while (!antenna.is_valid(design)) {
			for (int i = 0; i < numAntenna - 1; i++) {
				double antennaPoint = ThreadLocalRandom.current().nextDouble(0, aperture);
				if (antennaPoint < aperture && antennaPoint > 0) {
					design[i] = antennaPoint;
				}
			}
			design[design.length - 1] = aperture;
		}
		return design;
	}

	private double[] particleSwarm(int time) {
		int swarmSize = (int) (20 + Math.sqrt(numAntenna));
		Particle[] swarm = new Particle[swarmSize];

		for (int i = 0; i < swarmSize; i++) {
			swarm[i] = new Particle(numAntenna, angle, antenna);
		}

		double[] bestPos = swarm[0].generateRandomPosition();
		double peakSLL = antenna.evaluate(bestPos);
		for (int i = 0; i < time; i++) {
			for (int j = 0; j < swarm.length; j++) {
				double[] currentPos = swarm[j].moveForward(bestPos);
				double currentSLL = antenna.evaluate(currentPos);
				if (currentSLL < peakSLL) {
					peakSLL = currentSLL;
					bestPos = currentPos;
				}
			}
		}
		return bestPos;
	}

}
