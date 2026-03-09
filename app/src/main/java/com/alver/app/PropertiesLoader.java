package com.alver.app;

import com.alver.core.util.Entry;
import com.alver.core.util.EntryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesLoader {
	private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
	
	public static AppProperties load(String[] args) throws IOException {
		Map<String, String> propMap = parseProperties("/default.properties");
		Map<String, String> pathMap = parsePath(Path.of("./app.properties"));
		Map<String, String> argsMap = parseArgs(args);
		AppProperties appProperties = AppProperties.from(propMap, pathMap, argsMap);
		
		//FIXME: Don't log sensitive configurations.
		log.atInfo().setMessage("Loaded AppProperties.")
			.addKeyValue("propMap", propMap)
			.addKeyValue("pathMap", pathMap)
			.addKeyValue("argsMap", argsMap)
			.addKeyValue("result", appProperties)
			.log();
		return appProperties;
	}
	
	static Map<String, String> parseArgs(String[] args) {
		return Arrays.stream(args)
			.map(arg -> arg.split("=", 2))
			.map(split -> EntryImpl.of(split[0].replace("--", ""), split[1]))
			.collect(Collectors.toMap(EntryImpl::key, EntryImpl::value));
	}
	
	static Map<String, String> parseProperties(String file) throws IOException {
		Properties props = new Properties();
		props.load(AppProperties.class.getResourceAsStream(file));
		return props.entrySet().stream()
			.map(entry -> EntryImpl.of(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())))
			.collect(Collectors.toMap(Entry::key, Entry::value));
	}
	
	static Map<String, String> parsePath(Path path) throws IOException {
		return Files.readAllLines(path).stream()
			.map(line -> line.split("=", 2))
			.map(split -> EntryImpl.of(split[0], split[1]))
			.collect(Collectors.toMap(Entry::key, Entry::value));
	}
}
