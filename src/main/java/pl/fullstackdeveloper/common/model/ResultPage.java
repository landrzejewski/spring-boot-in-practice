package pl.fullstackdeveloper.common.model;

import java.util.List;
import java.util.function.Function;

import static pl.fullstackdeveloper.common.model.Mappers.mapList;

public record ResultPage<T>(List<T> content, PageSpec pageSpec, int totalPages) {

    public <O> ResultPage<O> map(final Function<T, O> mapper) {
        return new ResultPage<>(mapList(content, mapper), pageSpec, totalPages);
    }

}
