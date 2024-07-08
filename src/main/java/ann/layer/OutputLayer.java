package ann.layer;

import ann.Neuron;
import ann.af.ActivationFunction;
import ann.af.ReLUActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class OutputLayer {
    private ArrayList<Neuron> neurons = new ArrayList<>();

    public OutputLayer() {

    }

    public OutputLayer(int numberOfNeurons, int numberOfInputs) {
        neurons = new ArrayList<>();

        for (int i = 0; i < numberOfNeurons; i++) {
            ActivationFunction reluFunction = new ReLUActivationFunction();
            ArrayList<Double> arr = new ArrayList<>();
            Neuron neuron = new Neuron(arr, 0.0, reluFunction);
            neuron.initializeWeights(numberOfInputs);
            neurons.add(neuron);
        }
    }


    public List<Neuron> getNeurons() {
        return neurons;
    }



    public List<Neuron> calculate(List<Neuron> inputs, List<Neuron> expectedOutputs, double learningRate) {
        List<Neuron> outputs = new ArrayList<>();

        for (int i = 0; i < neurons.size(); i++) {
            Neuron neuron = neurons.get(i);
            Neuron output = neuron.calculate(inputs);
            outputs.add(output);

            if (expectedOutputs != null && i < expectedOutputs.size()) {
                double reward = calculateReward(output.getValue(), expectedOutputs.get(i).getValue());
                neuron.adjustWeightsAndBias(reward, learningRate);
            }
        }

        return outputs;
    }


    private double calculateReward(double output, double expectedOutput) {
        // Reward calculation function
        // For example, negative absolute error can be used
        return -Math.abs(output - expectedOutput);
    }
}
