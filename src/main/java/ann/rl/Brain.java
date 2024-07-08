package ann.rl;

import ann.Main;
import ann.Neuron;
import ann.kernel.Kernel;
import ann.layer.NeuronLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ann.Main;


public class Brain {
    private Map<String, List<Kernel>> kernels = new HashMap<>();
    private List<Neuron> lastOutputs = new ArrayList<>();
    private HashMap<String, NeuronLayer> layers = new HashMap<>();
    private double reward = 0;

    public Brain() {
        this.kernels = new HashMap<>();
    }

    public void addLayer(String name, NeuronLayer layer) {
        layers.put(name, layer);
    }
    public NeuronLayer getLayer(String name) {
        return layers.get(name);
    }

    public void performReinforcementLearningStep(List<Neuron> outputs, double r) {
        for (Neuron output : outputs) {
            Neuron neuron = output;
            neuron.setLastInputs(outputs);
            neuron.adjustWeightsAndBias(r, Main.LEARNING_RATE);
        }
        //setReward(getReward() + r);
    }

    public List<Neuron> getLastOutputs() {
        return lastOutputs;
    }

    public void addKernel(String modality, Kernel kernel) {
        ArrayList<Kernel> k = new ArrayList<>();
        k.add(kernel);
        this.kernels.put(modality, k);
    }

    public void removeKernel(String modality, Kernel kernel) {
        List<Kernel> modalityKernels = this.kernels.get(modality);
        if (modalityKernels != null) {
            modalityKernels.remove(kernel);
        }
    }

    public List<Kernel> getKernels(String modality) {
        return this.kernels.get(modality);
    }


    public void input(String modality, List<Neuron> inputs) {
        List<Kernel> modalityKernels = this.kernels.get(modality);
        if (modalityKernels != null) {
            for (Kernel kernel : modalityKernels) {
                kernel.processInputs(inputs);
            }
        }
    }


    public double evaluateEffectiveness(List<Neuron> expectedOutputs) {
        List<Neuron> outputs = getLastOutputs();  // Assuming getLastOutputs is a method that returns the last calculated outputs
        double error = 0.0;
        for (int i = 0; i < outputs.size(); i++) {
            error += outputs.get(i).getValue() - expectedOutputs.get(i).getValue();

        }
        return 1 / (1 + error);  // This is just an example, you'd need to come up with your own scoring function
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }
}
