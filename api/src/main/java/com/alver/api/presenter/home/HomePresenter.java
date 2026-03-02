package com.alver.api.presenter.home;

import com.alver.api.presenter.app.HtmlPresenter;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePresenter {

    private final HtmlPresenter presenter;
    private final Mustache template;
    private final HomePageModel model;

    @Autowired
    public HomePresenter(
            HtmlPresenter presenter,
            MustacheFactory factory
    ) {
        this.presenter = presenter;
        this.template = factory.compile("com/alver/api/presenter/home/home.mustache");
        this.model = ImmutableHomePageModel.builder().title("Home Page").build();
    }

    @GetMapping(produces = "text/html")
    public ResponseEntity<String> render() {
        String html = presenter.renderPage(template, model);
        return ResponseEntity.ok(html);
    }
}
