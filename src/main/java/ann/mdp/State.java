package ann.mdp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class State {
    private String name;
    private Map<Action, State> transitions;
    private int state = 0;

    public State(String name) {
        this.name = name;
        this.transitions = new HashMap<>();
    }

    public State(int state) {
        this.name = UUID.randomUUID().toString();
        this.state = state;
        this.transitions = new HashMap<>();
    }

    public State(double[][] qTable) {
        this.name = UUID.randomUUID().toString();
        //this.state = state;
        this.transitions = new HashMap<>();
    }

    public Set<Action> getAvailableActions() {
        return transitions.keySet();
    }


    public void addTransition(Action action, State state) {
        transitions.put(action, state);
    }

    public State getNextState(Action action) {
        return transitions.get(action);
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}