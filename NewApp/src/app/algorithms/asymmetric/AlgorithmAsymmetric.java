package app.algorithms.asymmetric;

import java.text.DecimalFormat;

public class AlgorithmAsymmetric {

	private double alpha;
	private double beta;
	private double[] expectedY;
	private CalculationsAsymmetric calcs;
	DecimalFormat df = new DecimalFormat("#.##");

	public AlgorithmAsymmetric(double alpha, double beta, double[][] s, double[] r, double[] expectedY) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.expectedY = expectedY;
		this.calcs = new CalculationsAsymmetric(s,r);
	}

	public double[] outputs() {
		return calcs.mu(alpha, beta);
	}
	
	public double average(double[] array){
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum/array.length;
	}

	public double rSquared() {
		double[] output = outputs();
		double avg  = average(output);
		double firstSum =0;
		double secondSum =0;
		for (int i = 0; i < output.length; i++) {
			firstSum+=Math.pow(expectedY[i]-output[i],2);
			secondSum+=Math.pow(expectedY[i]-avg,2);
		}
		return 1 - (firstSum/secondSum);
	}

}
