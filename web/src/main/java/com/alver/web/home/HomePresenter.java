package com.alver.web.home;

import com.alver.web.app.HtmlPresenter;
import com.alver.web.component.Component;
import com.alver.web.presenter.Presenter;

public class HomePresenter implements Presenter {
	
	private final HtmlPresenter htmlPresenter;
	
	public HomePresenter(HtmlPresenter htmlPresenter) {
		this.htmlPresenter = htmlPresenter;
	}
	
	@Override
	public String render() {
		return htmlPresenter.renderContent(new Component() {
			@Override
			public String template() {
				return """
					Home Page
					""";
			}
		});
	}
}
