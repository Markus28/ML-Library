package machinelearning;

public interface StringBasedFactory<T> {
    T get(String name);
}
