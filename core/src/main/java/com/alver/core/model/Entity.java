package com.alver.core.model;

import com.alver.core.util.Immutable;
import java.util.Optional;

@Immutable
public interface Entity {

  Optional<Long> id();
}
