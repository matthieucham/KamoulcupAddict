package com.kamoulcup.mobile.addict.task.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * Classe m�re des parsers de feed qui doivent parser du XML.
 * 
 * @param <T>
 */
public abstract class AbstractXmlFeedParser<T> implements IFeedParser<T>
{

  /**
   * Parser de l'api XmlPull.
   */
  private XmlPullParser _xmlPullParser;

  /**
   * Encodage du flux à parser.
   */
  private String _encoding;

  /**
   * @return L'encodage utilisé pour parser le flux XML.
   */
  public final String getEncoding()
  {
    return _encoding;
  }

  /**
   * @return le parser.
   */
  public final XmlPullParser getXmlPullParser()
  {
    return _xmlPullParser;
  }

  /**
   * Constructeur.
   */
  public AbstractXmlFeedParser(String encoding)
  {
    _encoding = encoding;
    _xmlPullParser = Xml.newPullParser();
  }

  /**
   * Constructeur.
   */
  public AbstractXmlFeedParser()
  {
    _encoding = null;
    _xmlPullParser = Xml.newPullParser();
    try
    {
      _xmlPullParser.defineEntityReplacementText("&egrave;", "è");
      _xmlPullParser.defineEntityReplacementText("&eacute;", "é");
      _xmlPullParser.defineEntityReplacementText("&ecirc;", "ê");
      _xmlPullParser.defineEntityReplacementText("&euml;", "ë");
    }
    catch (XmlPullParserException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public List<T> parseFeed(InputStream in) throws FeedParserException
  {
    try
    {
      _xmlPullParser.setInput(in, _encoding);
      return readXml(_xmlPullParser);
    }
    catch (XmlPullParserException e)
    {
      throw new FeedParserException("Impossible de positionner l'input du parser", e);
    }
    finally
    {
      try
      {
        in.close();
      }
      catch (IOException e)
      {
        // Pas grave ?
        e.printStackTrace();
      }
    }
  }

  /**
   * Parse le flux XML pour produire les objets attendus.
   * 
   * @param p_xmlPullParser
   * @return
   */
  protected abstract List<T> readXml(XmlPullParser xmlPullParser) throws FeedParserException;
}
