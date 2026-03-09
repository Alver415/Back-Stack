package com.alver.web.app;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.inject.Inject;
import java.io.StringWriter;

public class HtmlPresenter {
	
	private static final String PATH = "com/alver/web/app/html.mustache";
	
	private final Mustache template;
	private final String title;
	private final String version;
	
	@Inject
	public HtmlPresenter(MustacheFactory factory, String title, String version) {
		this.template = factory.compile(PATH);
		this.title = title;
		this.version = version;
	}
	
	public String renderPage(Mustache pageTemplate, PageModel pageModel) {
		String content = render(pageTemplate, pageModel);
		AppModel appModel =
			AppModelImpl.builder()
				.app(
					AppImpl.builder()
						.title(title)
						.version(version)
						.header(HeaderImpl.builder()
							// TODO: Inject nav header
							.addEntityTypes("home", "user", "address")
							.build())
						.build())
				.page(PageImpl.of(content))
				.build();
		return render(template, appModel);
	}
	
	public String render(Mustache template, Object model) {
		StringWriter writer = new StringWriter();
		template.execute(writer, model);
		return writer.toString();
	}
}
