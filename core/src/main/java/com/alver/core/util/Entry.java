package com.alver.core.util;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface Entry<K, V> {
	@Value.Parameter
	K key();

	@Value.Parameter
	V value();

	static <K, V> Entry<K, V> of(K key, V value) {
		return ImmutableEntry.of(key, value);
	}

	static <K, V> List<Entry<K, V>> from(Map<K, V> map) {
		return map.entrySet().stream()
				.map(e -> Entry.of(e.getKey(), e.getValue()))
				.toList();
	}
}