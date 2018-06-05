package machinelearning;

public abstract class StringBasedFactory<T> {
    abstract T get(String name);
}
