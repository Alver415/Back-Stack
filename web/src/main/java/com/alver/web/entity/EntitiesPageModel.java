package com.alver.web.entity;

import static org.immutables.value.Value.Parameter;

import com.alver.core.model.Entity;
import com.alver.core.util.Immutable;
import com.alver.web.app.PageModel;
import com.alver.web.model.Model;
import java.util.List;

@Immutable
public interface EntitiesPageModel<T extends Entity> extends PageModel {

  @Parameter
  List<Model> entities();
}
