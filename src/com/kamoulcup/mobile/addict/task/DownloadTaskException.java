package com.kamoulcup.mobile.addict.task;

/**
 * Exception levées par les DownloadTasks.
 */
public class DownloadTaskException extends Exception
{
  public DownloadTaskException(String pDetailMessage, Throwable pThrowable)
  {
    super(pDetailMessage, pThrowable);
  }

}
