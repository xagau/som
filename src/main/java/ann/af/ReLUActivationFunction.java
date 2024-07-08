package ann.af;

import ann.util.Unity;

public class ReLUActivationFunction implements ActivationFunction {

    @Override
    public double activate(double input) {
        return Math.max(0, Unity.unity(input, Double.MAX_VALUE));
    }
}
