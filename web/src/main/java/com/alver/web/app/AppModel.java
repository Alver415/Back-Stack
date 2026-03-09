package com.alver.web.app;

import com.alver.core.util.Immutable;

import java.util.List;

@Immutable
public interface AppModel {
	
	App app();
	
	Page page();
	
	static AppModel of(String title, String version, String content) {
		return AppModelImpl.builder()
			.app(AppImpl.builder()
				.title(title)
				.version(version)
				.build())
			.page(PageImpl.builder()
				.content(content)
				.build())
			.build();
	}
	
	@Immutable
	interface App {
		String title();
		
		String version();
		
		Header header();
		
		@Immutable
		interface Header {
			List<String> entityTypes();
		}
	}
	
	@Immutable
	interface Page {
		String content();
	}
}
