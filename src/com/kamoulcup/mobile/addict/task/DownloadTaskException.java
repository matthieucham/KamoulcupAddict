package com.kamoulcup.mobile.addict.task;

/**
 * Exception lev�es par les DownloadTasks.
 */
public class DownloadTaskException extends Exception
{
  public DownloadTaskException(String pDetailMessage, Throwable pThrowable)
  {
    super(pDetailMessage, pThrowable);
  }

}
