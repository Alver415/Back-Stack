package com.alver.api.presenter.app;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface AppModel {

    @Value.Parameter
    App app();

    @Value.Parameter
    Page page();


    static AppModel of(String title, String version, String content) {
        return ImmutableAppModel.of(
                ImmutableApp.of(title, version),
                ImmutablePage.of(content)
        );
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface App {
        @Value.Parameter
        String title();

        @Value.Parameter
        String version();

        @Value.Default
        default Header header(){
            return ImmutableHeader.of(List.of("users", "addresses"));
        }

        @Value.Immutable
@Value.Style(jacksonIntegration = true)
        interface Header {
            @Value.Parameter
            List<String> entityTypes();
        }
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface Page {
        @Value.Parameter
        String content();
    }

}
