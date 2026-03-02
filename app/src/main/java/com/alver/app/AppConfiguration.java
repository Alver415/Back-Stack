package com.alver.app;

import com.alver.core.model.Address;
import com.alver.core.model.User;
import com.alver.data.DatabaseClient;
import com.alver.data.EntityRepository;
import com.alver.data.Repository;
import com.alver.data.reader.Reader;
import com.alver.data.writer.EntityWriter;
import com.alver.data.writer.Writer;
import com.alver.app.service.EntityService;
import com.alver.app.service.IService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    //region Reader

//    @Bean
//    public Reader<User> userReader(DatabaseClient db) {
//        return new EntityReader<>(db);
//    }
//
//    @Bean
//    public Reader<Address> addressReader(DatabaseClient db) {
//        return new EntityReader<>(db);
//    }

    //endregion Reader


    //region Writer

    @Bean
    public Writer<User> userWriter(DatabaseClient databaseClient) {
        return new EntityWriter<>(databaseClient);
    }

    @Bean
    public Writer<Address> addressWriter(DatabaseClient databaseClient) {
        return new EntityWriter<>(databaseClient);
    }

    //endregion Writer


    //region Repository

    @Bean
    public Repository<User> userRepository(
            Reader<User> reader,
            Writer<User> writer
    ) {
        return new EntityRepository<>(reader, writer);
    }

    @Bean
    public Repository<Address> addressRepository(
            Reader<Address> reader,
            Writer<Address> writer
    ) {
        return new EntityRepository<>(reader, writer);
    }

    //endregion Repository


    //region Service

    @Bean
    public IService<User> userService(Repository<User> repository) {
        return new EntityService<>(repository);
    }

    @Bean
    public IService<Address> addressService(Repository<Address> repository) {
        return new EntityService<>(repository);
    }

    //endregion Service
}
