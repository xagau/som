package ann.rl;

import ann.Neuron;
import ann.kernel.Kernel;
import ann.kernel.SimpleKernel;
import ann.mdp.Action;
import ann.mdp.MDP;
import ann.mdp.State;

import java.util.*;

public class Agent {
    private MDP mdp;
    private Map<State, Map<Action, Double>> qValues;
    private Brain brain = new Brain();

    public Agent(MDP mdp) {
        this.mdp = mdp;
        this.mdp.addState("start");
        this.mdp.addAction("Up");
        this.mdp.addAction("Down");
        this.mdp.addAction("Right");
        this.mdp.addAction("Left");
        qValues = new HashMap<>();
        for (State state : mdp.getStates()) {
            qValues.put(state, new HashMap<>());
            for (Action action : state.getAvailableActions()) {
                qValues.get(state).put(action, 0.0);
            }
        }
    }

    public List<Neuron> getOutputs() {
        // Implement the logic to retrieve the outputs of the neural network
        // Return the list of output neurons
        return brain.getLayer("output").getNeurons();
    }

    private int getOptimalAction(int state) {


        Action optimalAction = getBestAction(qTable, state );
        double maxQValue = qTable[state][0];

        for (int action = 1; action < qTable[state].length; action++) {
            if (qTable[state][action] > maxQValue) {
                action = optimalAction.getId() ;
                maxQValue = qTable[state][action];
            }
        }

        return optimalAction.getId();
    }

    public void receiveInputs(List<Neuron> inputs) {
        List<Kernel> k = brain.getKernels("input");
        if( k == null ) {
            Kernel sk = new SimpleKernel();
            sk.processInputs(inputs);
            brain.addKernel("input", sk);
        }
        List<Kernel> ken = brain.getKernels("input");
        List<Neuron> lst = new ArrayList<>();
        List<Neuron> ls = new ArrayList<>();
        for(int i =0; i < ken.size(); i++) {
            Kernel kk = ken.get(i);
            lst = kk.processInputs(inputs);
            ls.addAll(lst);
            //lst.clear();
        }

        // Convert processedInputs to an array of input values
        double[] inputValues = new double[lst.size()];
        lst.clear();
        System.out.println(inputValues.length);
        for (int i = 0; i < ls.size(); i++) {
            inputValues[i] = ls.get(i).getValue();
        }

        // Use the SARSA algorithm to update Q-values and choose an action
        double learningRate = 0.1; // Adjust the learning rate as needed
        double discountFactor = 0.9; // Adjust the discount factor as needed

        // Perform SARSA update and action selection based on the input values
        int currentState = getState(inputValues);
        int currentAction = chooseAction(inputValues);
        int optimalNextAction = -1;
        // Simulate an environment and update Q-values
        for (int i = 0; i < numEpisodes; i++) {
            int nextState = simulateNextState(currentAction);
            optimalNextAction = getOptimalAction(nextState); //currentState, currentAction, nextState);
            double reward = 0;

            int nextAction = 0;
             if( optimalNextAction != -1 ) {
                reward = getReward(currentState, optimalNextAction, nextState);
            } else {
                 nextAction = chooseAction(inputValues);
                 reward = getReward(currentState, nextAction, nextState);
            }
            System.out.println("ann.rl.Agent Reward:" + reward);
            System.out.println("ann.rl.Agent ann.mdp.Action" + nextAction);
            System.out.println("ann.rl.Agent Next ann.mdp.State" + nextState);

            double nextQ = qTable[nextState][optimalNextAction];
            //int nextAction = chooseAction(nextState);

            // SARSA update
            double currentQ = qTable[currentState][currentAction];

            double updatedQ = currentQ + learningRate * (reward + discountFactor * nextQ - currentQ);
            qTable[currentState][currentAction] = updatedQ;

            currentState = nextState;
            currentAction = nextAction;
        }

        int nextState = simulateNextState(currentAction);
        // Choose the next action based on the epsilon-greedy policy
        int nextAction = chooseAction(inputValues);


        // Get the Q-values for the current state-action pair
        double currentQ = qTable[currentState][currentAction];

        // Get the Q-value for the next state and next action
        double nextQ = qTable[nextState][nextAction];

        // Calculate the updated Q-value using the SARSA update equation
        double updatedQ = currentQ + learningRate * (this.getReward(currentState, currentAction, nextState) + discountFactor * nextQ - currentQ);

        // Update the Q-value in the Q-table
        qTable[currentState][currentAction] = updatedQ;

        // Take necessary actions based on the SARSA output
        if (reward > maxReward) {
            // Update the maximum reward and save the optimal action
            maxReward = reward;
        }
    }

    double maxReward = 1;
    double reward = 0;

    public Action getBestAction(Map<Action, Double> av, int state) {
        Map<Action, Double> actionValues = qValues.get(state);
        return Collections.max(actionValues.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Action getBestAction(double[][] qTable, int state){
        Action action = new Action("best");
        return action;
        //return Collections.max(actionValues.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private int getState(double[] inputValues) {
        int state = 0;

        // Perform some processing or mapping of input values to create the state representation
        // You can use conditions, calculations, or any other logic to determine the state

        // Example: Assign a state based on the range of input values
        if (inputValues[0] < 0.5) {
            state = 0;
        } else if (inputValues[0] < 1.0) {
            state = 1;
        } else {
            state = 2;
        }

        return state;
    }

    private int getRandomAction() {
        // Generate a random action from the available action space
        // For example, if you have three possible actions (0, 1, 2)
        // you can use a random number generator to select one of them
        int numActions = 3; // Modify this based on your available actions
        int randomAction = (int) (Math.random() * numActions);
        return randomAction;
    }

    private int getGreedyAction(int state) {
        // Find the action with the highest Q-value for the given state
        // Traverse the Q-table or use other methods to determine the action
        // Here, we assume you have a qTable 2D array where the rows represent states and the columns represent actions
        int bestAction = 0;
        double bestQValue = qTable[state][0];

        for (int action = 1; action < qTable[state].length; action++) {
            if (qTable[state][action] > bestQValue) {
                bestAction = action;
                bestQValue = qTable[state][action];
            }
        }

        return bestAction;
    }

    double decayFactor = 0.1;
    private int chooseAction(int state, double explorationRate) {
        int action;

        // Determine whether to explore or exploit based on the exploration rate
        if (Math.random() < explorationRate) {
            // Exploration: Choose a random action
            action = getRandomAction();
        } else {
            // Exploitation: Choose the action with the highest Q-value for the current state
            action = getGreedyAction(state);
        }

        // Decay the exploration rate over time (e.g., by multiplying with a decay factor)
        explorationRate *= decayFactor;

        return action;
    }


    public int chooseAction(double[] state) {
        int action = 0;

        // Implement the logic to choose an action based on the current state
        // You can use conditions, calculations, or any other logic to determine the action

        // Example: Assign different actions based on the current state
        switch ((int) state[0]) {
            case 0:
                action = 1;
                break;
            case 1:
                action = 2;
                break;
            case 2:
                action = 3;
                break;
            default:
                // Handle any default case or assign a default action
                action = 0;
                break;
        }

        return action;
    }

    private int simulateNextState(int action) {
        int nextState = 0;

        // Implement the logic to simulate the next state based on the chosen action
        // You can use conditions, calculations, or any other logic to determine the next state

        // Example: Increment the action to get the next state
        nextState = action + 1;

        return nextState;
    }

    private double getReward(int currentState, int currentAction, int nextState) {
        double reward = 0.0;

        // Implement the logic to calculate the reward based on the current state, action, and next state
        // You can use conditions, calculations, or any other logic to determine the reward

        // Example: Assign a reward based on a specific condition
        if (currentState == 0 && nextState == 1) {
            reward = 1.0;
        } else {
            reward = -0.01;
        }

        return reward;
    }

    // Define your Q-table as a 2D array
    private double[][] qTable = new double[10][10];
    public void initialize() {
        for(int i = 0; i < qTable.length;i++){
            for(int j = 0; j < qTable.length; j++) {
                qTable[i][j] = Math.random();
            }
        }
    }
    // Define the number of episodes for training
    private int numEpisodes = 10;
}
