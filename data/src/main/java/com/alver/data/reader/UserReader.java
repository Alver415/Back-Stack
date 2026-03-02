package com.alver.data.reader;

import com.alver.core.model.Address;
import com.alver.core.model.ImmutableAddress;
import com.alver.core.model.ImmutableUser;
import com.alver.core.model.User;
import com.alver.data.DatabaseClient;
import com.alver.data.SqlResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserReader extends EntityReader<User> {

    private static final String SELECT = SqlResourceLoader
            .load("/sql/select-users.sql");

    @Autowired
    protected UserReader(DatabaseClient databaseClient) {
        super(databaseClient);
    }

    @Override
    public List<User> findAll() {
        List<Map<String, Object>> results = databaseClient.query(SELECT);
        return results.stream().map(map -> (User) ImmutableUser.builder()
                        .id((long) map.get("ID"))
                        .firstName((String) map.get("FIRST_NAME"))
                        .lastName((String) map.get("LAST_NAME"))
                        .email((String) map.get("EMAIL"))
                        .phone((String) map.get("PHONE"))
                        //TODO: Implement join (currently hard-coded stub value)
                        .primaryAddress(ImmutableAddress.of(
                                Optional.of(1L),
                                "123",
                                "Boston",
                                Address.USState.MA,
                                "01234",
                                false))
                        .build())
                .toList();
    }
}
