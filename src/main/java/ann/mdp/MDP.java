package ann.mdp;

import ann.rl.Agent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MDP {
    private Map<String, State> states = new HashMap<>();
    private Map<String, Action> actions= new HashMap<>();
    private Agent agent = new Agent(this);
    private double reward = 0.1;
    public MDP() {
        states = new HashMap<>();
        actions = new HashMap<>();
    }



    public void addState(String name) {
        states.putIfAbsent(name, new State(name));
    }

    public void addAction(String name) {
        actions.putIfAbsent(name, new Action(name));
    }

    public void addTransition(String fromState, String actionName, String toState) {
        Action action = actions.get(actionName);
        states.get(fromState).addTransition(action, states.get(toState));
    }

    public String getNextState(String currentState, String actionName) {
        Action action = actions.get(actionName);
        return states.get(currentState).getNextState(action).getName();
    }

    public Collection<State> getStates() {
        return states.values();
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}