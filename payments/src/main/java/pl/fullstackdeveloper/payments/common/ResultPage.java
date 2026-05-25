package pl.fullstackdeveloper.payments.common;

import java.util.List;
import java.util.function.Function;

public record ResultPage<T>(List<T> content, PageSpec pageSpec, int totalPages) {

    public <O> ResultPage<O> map(final Function<T, O> mapper) {
        return new ResultPage<>(Mappers.mapList(content, mapper), pageSpec, totalPages);
    }

}
