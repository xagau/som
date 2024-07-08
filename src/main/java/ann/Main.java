package ann;

import ann.kernel.Kernel;
import ann.kernel.SimpleKernel;
import ann.layer.NeuronLayer;
import ann.mdp.MDP;
import ann.rl.Agent;
import ann.rl.Brain;
import ann.util.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final double LEARNING_RATE = 0.01;

    public static void main(String[] args) {
        // Initialize the network with some default values
        int numberOfInputs = 5;
        int numberOfNeurons = 10;
        NeuronLayer layer = new NeuronLayer(numberOfNeurons, numberOfInputs);

        // Set some random initial weights for the neurons
        double[] inputs = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        for (Neuron neuron : layer.getNeurons()) {
            List<Double> weights = new ArrayList<>();
            for (int i = 0; i < numberOfInputs; i++) {
                weights.add(Math.random()); // Set random weights between 0 and 1
            }
            neuron.setWeights(weights);
        }

        // Create a kernel
        Kernel k = new SimpleKernel();
        List<Neuron> kk = k.processInputs(Utils.convertToN(inputs));

        // Create a brain and add our layer and kernel
        Brain brain = new Brain();
        brain.addLayer("input", layer);
        brain.addKernel("input", k);

        // Convert the inputs to ann.neuron.Neuron objects
        System.out.println("kk:" + kk.size());
        List<Neuron> inputNeurons = kk;

        // Set the input neurons to the agent
        MDP mdp = new MDP();
        Agent agent = new Agent(mdp);
        agent.receiveInputs(inputNeurons);

        // Perform reinforcement learning steps
        int epoch = 5;
        for (int i = 0; i < epoch; i++) {
            // Get the outputs from the agent
            List<Neuron> outputs = agent.getOutputs();

            // Print the outputs
            DecimalFormat df = new DecimalFormat("0.0000000000000");
            int ctr = 0;
            for (Neuron output : outputs) {
                double co = output.calculate(outputs).getValue();
                output.setError(inputs[ctr] - co);
                output.setValue(co + output.getError());
                System.out.println("ann.neuron.Neuron ID: " + output.getId() + ", calculated output: " + df.format(co));
                // Perform a reinforcement learning step with some reward
                brain.performReinforcementLearningStep(outputs, Main.LEARNING_RATE);
                System.out.println("Reward: " + df.format(output.getReward()));
                System.out.println("Bias: " + df.format(output.getBias()));
                System.out.println("Value: " + df.format(output.getValue()));
                System.out.println("Expected Value: " + df.format(inputs[ctr]));
                System.out.println("Error: " + df.format(inputs[ctr] - output.getValue()));
                System.out.println("Effectiveness of Results: " + df.format(brain.evaluateEffectiveness(outputs)));
                System.out.println("--------------------------------------------------------------------------");
                ctr++;
            }

            Agent a = mdp.getAgent();
            a.initialize();
            while(true){
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                System.out.println("ann.rl.Agent Reward:" + a.reward);
                a.chooseAction(inputs);
            }
        }
    }

}

