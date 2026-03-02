package com.alver.data.reader;

import com.alver.core.model.Address;
import com.alver.core.model.ImmutableAddress;
import com.alver.data.DatabaseClient;
import com.alver.data.SqlResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AddressReader extends EntityReader<Address> {

    private static final String SELECT = SqlResourceLoader
            .load("/sql/select-addresses.sql");

    @Autowired
    protected AddressReader(DatabaseClient databaseClient) {
        super(databaseClient);
    }

    @Override
    public List<Address> findAll() {
        List<Map<String, Object>> results = databaseClient.query(SELECT);
        return results.stream().map(map -> (Address) ImmutableAddress.builder()
                .id((long) map.get("ID"))
                .street((String) map.get("STREET"))
                .city((String) map.get("CITY"))
                .state(Address.USState.valueOf((String) map.get("STATE")))
                .zipCode((String) map.get("ZIP_CODE"))
                .isApartment((boolean) map.get("IS_APARTMENT"))
                .build()).toList();
    }
}
