package com.alver.api;

import com.alver.data.DatabaseClient;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class DatabaseApi {

  private final DatabaseClient databaseClient;

  @Inject
  public DatabaseApi(DatabaseClient databaseClient) {
    this.databaseClient = databaseClient;
  }

  public List<Map<String, Object>> query(String sql) {
    return databaseClient.query(sql);
  }

  public void execute(String sql) {
    databaseClient.execute(sql);
  }
}
