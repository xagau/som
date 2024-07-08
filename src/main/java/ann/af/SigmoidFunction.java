package ann.af;

public class SigmoidFunction implements ActivationFunction {
    @Override
    public double activate(double weightedSum) {
        return 1 / (1 + Math.exp(-weightedSum));
    }
}