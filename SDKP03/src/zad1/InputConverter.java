package zad1;

import java.util.function.Function;

public class InputConverter<T> {

    private final T fname;

    public InputConverter(T fname) {
        this.fname = fname;
    }

    public <R> R convertBy(Function... functions) {
        Object input = fname;
        Object output = null;
        for (Function f : functions) {
            output = f.apply(input);
            input = output;
        }
        return (R) output;
    }
}
