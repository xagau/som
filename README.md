# Artificial Neural Network Package

## Overview
This package provides an implementation of an artificial neural network (ANN) that includes components for kernel processing, neuron layers, Markov Decision Processes (MDP), reinforcement learning (RL) agents, and utility functions. The main class demonstrates how to set up and train a neural network with a simple kernel and neuron layer.

## Installation
To install and run this package, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/xagau/som.git
   cd som


## Build the Project:
Ensure you have Java Development Kit (JDK) installed. Then, compile the project using:

bash
```
javac -d bin src/ann/Main.java
```
Run the Project:

bash
```
    java -cp bin ann.Main
```
## Usage

The Main.java class demonstrates the basic setup and training process of the neural network. It includes:

    Initialization of a neuron layer with random weights.
    Creation of a simple kernel for input processing.
    Setup of a brain with layers and kernels.
    Configuration and training of a reinforcement learning agent.

## Key Components

    Kernel: Processes inputs and converts them into neuron objects.
    NeuronLayer: Represents a layer of neurons with specified weights.
    MDP: Defines the environment for the RL agent.
    Agent: Performs actions in the MDP environment and learns from the rewards.

## Example

Here's a brief example of how to set up and run the neural network:

java
```
public class Main {
    public static void main(String[] args) {
        // Initialize the network with some default values
        int numberOfInputs = 5;
        int numberOfNeurons = 10;
        NeuronLayer layer = new NeuronLayer(numberOfNeurons, numberOfInputs);

        // Set random initial weights for the neurons
        double[] inputs = {0.1, 0.2, 0.3, 0.4, 0.5};
        for (Neuron neuron : layer.getNeurons()) {
            List<Double> weights = new ArrayList<>();
            for (int i = 0; i < numberOfInputs; i++) {
                weights.add(Math.random()); // Set random weights between 0 and 1
            }
            neuron.setWeights(weights);
        }

        // Create a kernel and process inputs
        Kernel k = new SimpleKernel();
        List<Neuron> processedInputs = k.processInputs(Utils.convertToN(inputs));

        // Create a brain and add layers and kernels
        Brain brain = new Brain();
        brain.addLayer("input", layer);
        brain.addKernel("input", k);

        // Set up the agent with the MDP
        MDP mdp = new MDP();
        Agent agent = new Agent(mdp);
        agent.receiveInputs(processedInputs);

        // Perform reinforcement learning
        int epochs = 5;
        for (int i = 0; i < epochs; i++) {
            List<Neuron> outputs = agent.getOutputs();
            brain.performReinforcementLearningStep(outputs, Main.LEARNING_RATE);
            // ... (other operations)
        }
    }
}

```

## Contributing

If you would like to contribute to this project, please follow these steps:

    Fork the repository.
    Create a new branch (git checkout -b feature-branch).
    Make your changes.
    Commit your changes (git commit -m 'Add some feature').
    Push to the branch (git push origin feature-branch).
    Open a pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
Contact

If you have any questions or suggestions, please contact xagau
