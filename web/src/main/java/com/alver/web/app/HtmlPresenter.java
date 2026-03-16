package com.alver.web.app;

import com.alver.web.component.Component;
import com.alver.web.css.CssStyleSheet;
import com.alver.web.css.CssStyleSheetImpl;

import javax.inject.Inject;

public class HtmlPresenter {
	
	private final String title;
	
	private final String version;
	
	private final CssStyleSheet styleSheet;
	
	@Inject
	public HtmlPresenter(
		String title,
		String version,
		CssStyleSheet styleSheet
	) {
		this.title = title;
		this.version = version;
		this.styleSheet = styleSheet;
	}
	
	public String renderContent(Component<?> component) {
		return HtmlComponentImpl.of(
			title,
			version,
			styleSheet,
			component
		).renderTemplate();
	}
}
