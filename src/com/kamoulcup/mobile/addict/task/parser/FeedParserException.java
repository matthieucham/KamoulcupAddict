package com.kamoulcup.mobile.addict.task.parser;

/**
 * Exception survenant lors d'un parsing de feed.
 */
public class FeedParserException extends Exception
{
  public FeedParserException(String pDetailMessage, Throwable pThrowable)
  {
    super(pDetailMessage, pThrowable);
  }

}
