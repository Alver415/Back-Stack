package com.alver.api.presenter.app;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

@Component
public class HtmlPresenter {

    private static final String PATH = "com/alver/api/presenter/app/html.mustache";

    private final Mustache template;
    private final String title;
    private final String version;

    @Autowired
    public HtmlPresenter(
            MustacheFactory factory,
            @Value("${app.title}") String title,
            @Value("${app.version}") String version
    ) {
        this.template = factory.compile(PATH);
        this.title = title;
        this.version = version;
    }

    public String renderPage(Mustache pageTemplate, PageModel pageModel) {
        String content = render(pageTemplate, pageModel);
        AppModel appModel = ImmutableAppModel.builder()
                .app(ImmutableApp.builder()
                        .title(title)
                        .version(version)
                        .header(ImmutableHeader.builder()
                                .entityTypes(List.of("users", "addresses"))
                                .build())
                        .build())
                .page(ImmutablePage.of(content))
                .build();
        return render(template, appModel);
    }

    public String render(Mustache template, Object model) {
        StringWriter writer = new StringWriter();
        template.execute(writer, model);
        return writer.toString();
    }
}
