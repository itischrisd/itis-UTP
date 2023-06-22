package zad3;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T> {

    public XList() {
        super();
    }

    public XList(Collection<T> collection) {
        super(collection);
    }

    public XList(T... elements) {
        super(Arrays.asList(elements));
    }

    public static <T> XList<T> of(T... elements) {
        return new XList<T>(Arrays.asList(elements));
    }

    public static <T> XList<T> of(Collection<T> collection) {
        return new XList<T>(collection);
    }

    public static XList<String> tokensOf(String string, String regex) {
        return XList.of(string.split(regex));
    }

    public static XList<String> tokensOf(String string) {
        return XList.tokensOf(string, "\\s");
    }

    public static XList<String> charsOf(String string) {
        return XList.tokensOf(string, "");
    }

    public XList<T> union(Collection<T> collection) {
        XList<T> result = XList.of(this);
        result.addAll(collection);
        return result;
    }

    public XList<T> union(T... elements) {
        return this.union(XList.of(elements));
    }

    public XList<T> diff(Collection<T> collection) {
        XList<T> result = new XList<>();
        for (T t : this) {
            if (!collection.contains(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public XList<T> unique() {
        return new XList<>(this.stream().distinct().collect(Collectors.toList()));
    }

    public XList<XList<T>> combine() {
        return combine((List<? extends Collection<T>>) this);
    }

    public XList<XList<T>> combine(List<? extends Collection<T>> lists) {
        XList<XList<T>> combinations = new XList<>();
        combineHelper(combinations, new XList<>(), lists, lists.size() - 1);
        return combinations;
    }

    private void combineHelper(XList<XList<T>> combinations, XList<T> current, List<? extends Collection<T>> lists, int index) {
        if (index == -1) {
            XList<T> toAdd = new XList<>(current);
            Collections.reverse(toAdd);
            combinations.add(new XList<>(toAdd));
            return;
        }
        for (T element : lists.get(index)) {
            current.add(element);
            combineHelper(combinations, current, lists, index - 1);
            current.remove(current.size() - 1);
        }
    }

    public String join(String separator) {
        return this.stream().map(Object::toString).collect(Collectors.joining(separator));
    }

    public String join() {
        return join("");
    }

    public <K> XList<String> collect(Function<XList<K>, String> function) {
        XList xList = new XList();
        for (XList<K> t : ((XList<XList<K>>) this)) {
            xList.add(function.apply(t));
        }
        return xList;
    }

    public void forEachWithIndex(BiConsumer<T, Integer> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i), i);
        }
    }
}
