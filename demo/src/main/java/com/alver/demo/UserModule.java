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
import com.alver.demo.model.User;
import com.alver.demo.model.UserCreateRequest;
import com.alver.demo.model.UserUpdateRequest;
import com.alver.demo.repository.UserMapper;
import com.alver.web.app.HtmlPresenter;
import com.alver.web.entity.EntityPresenter;
import com.alver.web.presenter.Presenter;
import com.github.mustachejava.MustacheFactory;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public interface UserModule {
	
	@Provides
	@IntoMap
	@StringKey("user")
	static Presenter userPresenter(
		Service<User> userService,
		HtmlPresenter htmlPresenter,
		MustacheFactory mustacheFactory) {
		return new EntityPresenter<>(userService, htmlPresenter, mustacheFactory);
	}
	
	@Provides
	@IntoMap
	@StringKey("user")
	static CrudApi<?> userApi(Service<User> userService) {
		return new EntityApi<>(
			userService,
			UserCreateRequest.class,
			UserUpdateRequest.class
		);
	}
	
	@Provides
	static Service<User> userService(Repository<User> userRepository) {
		return new EntityService<>(userRepository);
	}
	
	@Provides
	static Repository<User> userRepository(
		Reader<User> userReader, Writer<User> userWriter) {
		return new EntityRepository<>(userReader, userWriter);
	}
	
	@Provides
	static Reader<User> userReader(DatabaseClient databaseClient) {
		return new EntityReader<>(
			databaseClient,
			new UserMapper(),
			SqlResourceLoader.load("/sql/select-users.sql")
		);
	}
	
	@Provides
	static Writer<User> userWriter(DatabaseClient databaseClient) {
		return new EntityWriter<>(databaseClient);
	}
}
