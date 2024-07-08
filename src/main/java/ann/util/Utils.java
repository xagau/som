package ann.util;

import ann.af.ReLUActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static double convertDouble(double d) {
        if(d < 0) {
            d = Math.abs(d); //throw new IllegalArgumentException("Negative value provided: " + d);
        }
        return d / (Double.MAX_VALUE / 2);
    }
    public static List<Neuron> convertToN(double[] values) {
        List<Neuron> neurons = new ArrayList<>();
        ArrayList<Double> v = new ArrayList<>();
        for (int n = 0; n < values.length; n++) {
            v.add(new Double(values[n]));
        }
        for (int i = 0; i < values.length; i++) {
            Neuron neuron = new Neuron(v, 0.0, new ReLUActivationFunction());
            neurons.add(neuron);
        }
        return neurons;
    }
}
