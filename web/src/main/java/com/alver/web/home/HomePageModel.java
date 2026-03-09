package com.alver.web.home;

import static org.immutables.value.Value.Default;

import com.alver.core.util.Immutable;
import com.alver.web.app.PageModel;
import com.alver.web.component.Component;
import com.alver.web.component.ComponentImpl;
import java.util.Optional;

@Immutable
public interface HomePageModel extends PageModel {

  @Default
  default Optional<String> title() {
    return Optional.of("Hello World!");
  }

  @Default
  default Component<?> component() {
    return ComponentImpl.builder().build();
  }
}
