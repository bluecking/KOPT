
public class Temperature {
    public static void setTemperatureByAlpha(double t0, double alpha, double[] T) {
        if (t0 == 0) {
            // Throw exception
        }

        T[0] = t0;
        for (int i = 1; i < T.length; i++) {
            T[i] = alpha * T[i - 1];
        }
    }

    public static void setConstantTemperature(int constant, double[] t) {
        for (int i = 0; i < t.length; i++) {
            t[i] = constant;
        }
    }
}
