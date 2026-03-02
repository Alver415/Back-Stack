package com.alver.api.presenter.user;

import com.alver.api.presenter.model.Model;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface UserModel extends Model<UserModel> {


}
