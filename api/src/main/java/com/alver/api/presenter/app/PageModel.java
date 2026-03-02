package com.alver.api.presenter.app;

import org.immutables.value.Value;

import java.util.Optional;

public interface PageModel {

    Optional<String> title();

    Optional<String> style();

    Optional<String> scripts();
}
