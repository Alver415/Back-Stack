package com.alver.app;

import com.alver.core.util.Immutable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.immutables.value.Value.Default;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = AppPropertiesImpl.class)
public interface AppProperties {
	
	@Default
	default String title() {
		return "Back Stack";
	}
	
	@Default
	default String version() {
		return "0.0.0";
	}
	
	@Default
	default String databaseServerPath() {
		return "file:.database/back_stack";
	}

	@Default
	default String databaseServerName() {
		return "back_stack";
	}

	@Default
	default String databaseServerAddress() {
		return "localhost";
	}

	@Default
	default Integer databaseServerPort() {
		return 9000;
	}
	
	@Default
	default String databaseClientUrl() {
		return "jdbc:hsqldb:file:.database/back_stack";
	}
	
	@Default
	default String databaseClientUser() {
		return "sa";
	}
	
	@Default
	default String databaseClientPass() {
		return "";
	}
	
	@Default
	default String httpServerHost() {
		return "localhost";
	}
	
	@Default
	default int httpServerPort() {
		return 8080;
	}
	
	@Default
	default int httpServerBacklog() {
		return 10;
	}
	
	@SafeVarargs
	static AppProperties from(Map<String, String>... sources) {
		return from(Arrays.stream(sources)
			.map(Map::entrySet)
			.flatMap(Collection::stream)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n)));
	}
	
	static AppProperties from(Map<String, String> map) {
		return AppPropertiesImpl.builder()
			.title(map.get("title"))
			.version(map.get("version"))
			.databaseServerPath(map.get("database.server.path"))
			.databaseServerName(map.get("database.server.name"))
			.databaseServerAddress(map.get("database.server.address"))
			.databaseServerPort(Integer.parseInt(map.get("database.server.port")))
			.databaseClientPass(map.get("database.client.pass"))
			.databaseClientUrl(map.get("database.client.url"))
			.databaseClientUser(map.get("database.client.user"))
			.httpServerHost(map.get("http.server.host"))
			.httpServerPort(Integer.parseInt(map.get("http.server.port")))
			.httpServerBacklog(Integer.parseInt(map.get("http.server.backlog")))
			.build();
	}
}
