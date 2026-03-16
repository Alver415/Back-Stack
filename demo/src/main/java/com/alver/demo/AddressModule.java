package com.alver.demo;

import com.alver.api.CrudApi;
import com.alver.api.EntityApi;
import com.alver.app.service.EntityService;
import com.alver.app.service.Service;
import com.alver.data.DatabaseClient;
import com.alver.data.EntityRepository;
import com.alver.data.Repository;
import com.alver.data.SqlResourceLoader;
import com.alver.data.reader.EntityReader;
import com.alver.data.reader.Reader;
import com.alver.data.writer.EntityWriter;
import com.alver.data.writer.Writer;
import com.alver.demo.model.Address;
import com.alver.demo.model.AddressCreateRequest;
import com.alver.demo.model.AddressUpdateRequest;
import com.alver.demo.repository.AddressMapper;
import com.alver.web.app.HtmlPresenter;
import com.alver.web.presenter.EntityPresenter;
import com.alver.web.presenter.Presenter;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public interface AddressModule {
	
	@Provides
	@IntoMap
	@StringKey("address")
	static Presenter addressPresenter(
		Service<Address> addressService,
		HtmlPresenter htmlPresenter
	) {
		return new EntityPresenter<>(addressService, htmlPresenter);
	}
	
	@Provides
	@IntoMap
	@StringKey("address")
	static CrudApi<?> addressApi(Service<Address> addressService) {
		return new EntityApi<>(
			addressService,
			AddressCreateRequest.class,
			AddressUpdateRequest.class
		);
	}
	
	
	@Provides
	static Service<Address> addressService(Repository<Address> addressRepository) {
		return new EntityService<>(addressRepository);
	}
	
	@Provides
	static Repository<Address> addressRepository(
		Reader<Address> addressReader, Writer<Address> addressWriter) {
		return new EntityRepository<>(addressReader, addressWriter);
	}
	
	@Provides
	static Reader<Address> addressReader(DatabaseClient databaseClient) {
		return new EntityReader<>(
			databaseClient,
			new AddressMapper(),
			SqlResourceLoader.load("/sql/select-address.sql")
		);
	}
	
	@Provides
	static Writer<Address> addressWriter(DatabaseClient databaseClient) {
		return new EntityWriter<>(databaseClient);
	}
}
