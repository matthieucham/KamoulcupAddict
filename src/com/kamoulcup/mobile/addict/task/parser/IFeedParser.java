/******************************************************************************
 * Projet   : Octaèdre
 * Société  : Devinlec
 ******************************************************************************/
package com.kamoulcup.mobile.addict.task.parser;

import java.io.InputStream;
import java.util.List;

/**
 * Facade des parsers de "feeds".
 */
public interface IFeedParser<T>
{
  List<T> parseFeed(InputStream in) throws FeedParserException;
}
