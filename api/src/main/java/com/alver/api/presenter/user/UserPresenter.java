package com.alver.api.presenter.user;

import com.alver.core.model.User;
import com.alver.api.presenter.app.HtmlPresenter;
import com.alver.api.presenter.entity.EntityPresenter;
import com.alver.app.service.IService;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserPresenter extends EntityPresenter<User> {

    @Autowired
    public UserPresenter(
            IService<User> userService,
            HtmlPresenter presenter,
            MustacheFactory factory
    ) {
        super(userService, presenter, factory);
    }
}
