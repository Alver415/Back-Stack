package com.alver.api.presenter.home;

import com.alver.api.presenter.app.PageModel;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface HomePageModel extends PageModel {

    @Value.Default
    default Optional<String> title() {
        return Optional.of("Hello World!");
    }
}
