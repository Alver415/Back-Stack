package com.alver.api.presenter.user;

import com.alver.core.model.Address;
import com.alver.api.presenter.app.HtmlPresenter;
import com.alver.api.presenter.entity.EntityPresenter;
import com.alver.app.service.IService;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addresses")
public class AddressPresenter extends EntityPresenter<Address> {

    @Autowired
    public AddressPresenter(
            IService<Address> addressService,
            HtmlPresenter presenter,
            MustacheFactory factory
    ) {
        super(addressService, presenter, factory);
    }
}
