package machinelearning;

public abstract class StringBasedFactory<T> {
    public abstract T get(String name);
}
