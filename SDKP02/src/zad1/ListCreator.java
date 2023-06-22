package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> {

    private final List<T> items;

    private ListCreator(List<T> items) {
        this.items = items;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) result.add(item);
        }
        return new ListCreator<>(result);
    }

    public <R> List<R> mapEvery(Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T item : items) {
            result.add(function.apply(item));
        }
        return result;
    }
}
