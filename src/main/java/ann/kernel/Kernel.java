package ann.kernel;

import ann.Neuron;

import java.util.List;

public interface Kernel {

    List<Neuron> processInputs(List<Neuron> inputs);
}

