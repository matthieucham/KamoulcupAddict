package com.kamoulcup.mobile.addict.task;

import java.net.URL;
import java.util.List;

import android.os.AsyncTask;

import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.task.listener.IDownloadFeedTaskListener;
import com.kamoulcup.mobile.addict.task.parser.ResolutionFeedParser;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;

/**
 * Fabrique des tâches asynchrones utilisées par l'appli.
 */
public class TasksFactory
{
  /**
   * Instance de la factory
   */
  private static final TasksFactory INSTANCE = new TasksFactory();

  private TasksFactory()
  {
  }

  /**
   * @return Instance.
   */
  public static TasksFactory getInstance()
  {
    return INSTANCE;
  }

  /**
   * Fabrique la vraie implémentation de la tâche, avec accès distant et tout.
   * 
   * @param listener
   * @param ctx
   *          Context Android
   * @return
   */
  public AsyncTask<URL, FeedProgressInfo, List<Resolution>> buildDownloadResolutionsFeedTask(
    IDownloadFeedTaskListener<Resolution> listener)
  {
    return new DownloadFeedTask<Resolution>(listener, new ResolutionFeedParser());
  }

  public AsyncTask<URL, FeedProgressInfo, List<Resolution>> buildResolutionsMockTask(
    IDownloadFeedTaskListener<Resolution> listener)
  {
    return new MockDownloadResolutionTask(listener, 3000);
  }
}
