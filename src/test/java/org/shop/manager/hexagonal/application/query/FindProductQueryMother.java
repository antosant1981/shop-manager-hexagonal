package org.shop.manager.hexagonal.application.query;

import org.shop.manager.hexagonal.application.api.QueryData;
import org.shop.manager.hexagonal.vocabulary.*;

import java.util.List;

public class FindProductQueryMother {

    public static List<QueryData> aListOfProductsQueryData() {
        return List.of(aProductQueryData());
    }

    public static QueryData aProductQueryData() {
        return new QueryData(Identifier.nextVal(),
                new SerialNumber("e3566411-8ae9-404a-8917-924973a0446a"),
                new BarCode("1234567890_abcdeflopanfrtan_NMLP"),
                new Price(222.09),
                new ProductName("Electric drill"),
                "This is a brief description about the electric drill",
                Status.AVAILABLE);
    }
}
