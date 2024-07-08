package ann.kernel;

import ann.Neuron;
import ann.af.SigmoidFunction;

import java.util.ArrayList;
import java.util.List;

public class SimpleKernel implements Kernel {

    public List<Neuron> processInputs(List<Neuron> inputs) {
        List<Neuron> arr = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            for (Neuron input : inputs) {
                // Perform a simple computation by multiplying the input value by a constant factor
                double computedValue = 2.0 * input.getValue();

                // Apply the sigmoid activation function to the computed value
                SigmoidFunction sigmoid = new SigmoidFunction();
                double activatedValue = sigmoid.activate(computedValue);

                // Create a new output neuron with the activated value
                Neuron output = new Neuron(input, activatedValue);

                // Add the output neuron to the outputs list
                arr.add(output);
            }

        }
        return arr;
    }

}
