package com.alver.web.app;

import java.util.Optional;

public interface PageModel {

  Optional<String> title();

  Optional<String> style();

  Optional<String> scripts();
}
