package com.alver.api.presenter.entity;

import com.alver.core.model.Entity;
import com.alver.api.presenter.app.PageModel;
import com.alver.api.presenter.model.Model;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface EntitiesPageModel<T extends Entity> extends PageModel {

    @Value.Parameter
    List<Model> entities();
}
