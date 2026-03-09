package com.alver.web.home;

import com.alver.web.app.HtmlPresenter;
import com.alver.web.component.Component;
import com.alver.web.component.ComponentImpl;
import com.alver.web.css.*;
import com.alver.web.html.Attribute;
import com.alver.web.html.Div;
import com.alver.web.html.DivImpl;
import com.alver.web.presenter.Presenter;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.inject.Inject;
import java.util.List;

public class HomePresenter implements Presenter {
	
	private final HtmlPresenter presenter;
	private final Mustache template;
	
	@Inject
	public HomePresenter(HtmlPresenter presenter, MustacheFactory factory) {
		this.presenter = presenter;
		this.template = factory.compile("com/alver/web/home/home.mustache");
	}
	
	@Override
	public String render() {
		Component<?> component = ComponentImpl.builder().templateContent("").build();
		StyleAttribute style =
			StyleAttributeImpl.of(
				List.of(
					CssEntryImpl.of("border", "1px solid #f0ff"),
					CssColor.of(255, 255, 255, 255),
					CssBackgroundImpl.of(RGBAColor.of(255, 0, 255, 255))));
		
		List<Attribute<?, ?>> attributes =
			List.of(
				style,
				Attribute.of("checked", true),
				Attribute.of("unchecked", true),
				Attribute.of("absent", (Boolean) null));
		Div div = DivImpl.builder().attributes(attributes).addChildren(component).build();
		HomePageModel homePageModel =
			HomePageModelImpl.builder().title("Home Page").component(div).build();
		return presenter.renderPage(template, homePageModel);
	}
}
