package ann.util;

public class Unity {
    public static double unity(double flatten, double max) {
        if (flatten < 0) {
            flatten = Math.abs(flatten);
        } else if (flatten == 0) {
            return flatten;
        }
        double g = max;
        g = g / max / 2;
        return g;
    }
}
