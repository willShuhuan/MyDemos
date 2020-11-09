package com.dsh.txlessons.threadrxjava.network;

import com.dsh.threadrxjava.model.Repo;

import io.reactivex.Single;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
  @GET("users/{username}/repos")
  Single<List<Repo>> getRepos(@Path("username") String username);
}
