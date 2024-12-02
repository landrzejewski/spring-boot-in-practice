package pl.fullstackdeveloper.common;

import java.util.List;
import java.util.function.Function;

public interface Mappers {

    static <I, O> List<O> mapList(final List<I> input, final Function<I, O> mapper) {
        return input.stream().map(mapper).toList();
    }

}
