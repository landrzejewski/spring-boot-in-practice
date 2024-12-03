package pl.fullstackdeveloper.payments.cqrs.getcards;

import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.common.cqrs.Query;

public class GetCardsQuery implements Query<ResultPage<CardsProjection>> {

    private int pageNumber;
    private int pageSize;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
