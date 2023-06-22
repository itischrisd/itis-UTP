/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad1;

import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> {

    private final List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Selector<T> selector) {
        List<T> selected = new ArrayList<>();
        for (T item : list) {
            if (selector.select(item)) {
                selected.add(item);
            }
        }
        return new ListCreator<>(selected);
    }

    public <R> List<R> mapEvery(Mapper<T, R> mapper) {
        List<R> mapped = new ArrayList<>();
        for (T item : list) {
            mapped.add(mapper.map(item));
        }
        return mapped;
    }
}  
