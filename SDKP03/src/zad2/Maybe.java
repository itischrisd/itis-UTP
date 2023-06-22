package zad2;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    private final T t;

    private Maybe(T t) {
        this.t = t;
    }

    public static <R> Maybe<R> of(R r) {
        return new Maybe<>(r);
    }

    public void ifPresent(Consumer<T> cons) {
        if (isPresent()) cons.accept(t);
    }

    public <R> Maybe<R> map(Function<T, R> func) {
        return isPresent() ? new Maybe<>(func.apply(t)) : new Maybe<>(null);
    }

    public T get() {
        if (!isPresent()) throw new NoSuchElementException(" maybe is empty");
        return this.t;
    }

    public boolean isPresent() {
        return t != null;
    }

    public T orElse(T defVal) {
        return isPresent() ? t : defVal;
    }

    public Maybe<T> filter(Predicate<T> pred) {
        if (!isPresent() || pred.test(t)) return this;
        else return new Maybe<>(null);
    }

    @Override
    public String toString() {
        return isPresent() ? "Maybe has value " + this.t : "Maybe is empty";
    }
}
