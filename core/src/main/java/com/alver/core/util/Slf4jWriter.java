package com.alver.core.util;

import java.io.Writer;
import java.util.function.Consumer;
import org.jspecify.annotations.NonNull;

public class Slf4jWriter extends Writer {

  private final Consumer<String> logger;
  private final StringBuilder buffer = new StringBuilder();

  public Slf4jWriter(Consumer<String> logger) {
    this.logger = logger;
  }

  @Override
  public void write(char @NonNull [] buff, int off, int len) {
    for (int i = off; i < off + len; i++) {
      char c = buff[i];
      if (c == '\n') {
        flush();
      } else {
        buffer.append(c);
      }
    }
  }

  @Override
  public void flush() {
    if (!buffer.isEmpty()) {
      logger.accept(buffer.toString());
      buffer.setLength(0);
    }
  }

  @Override
  public void close() {
    flush();
  }
}
