package com.kamoulcup.mobile.addict.task.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.kamoulcup.mobile.addict.domain.Club;
import com.kamoulcup.mobile.addict.domain.Ekyp;
import com.kamoulcup.mobile.addict.domain.Joueur;
import com.kamoulcup.mobile.addict.domain.Offre;
import com.kamoulcup.mobile.addict.domain.Resolution;

/**
 * Parser des résolutions XML.
 */
public class ResolutionFeedParser extends AbstractXmlFeedParser<Resolution>
{
  /**
   * Encodage du flux Resolutions.
   */
  private static final String ENCODING = "UTF-8";

  // Déclaration des balises
  private final static String BALISE_RESOLUTIONS = "resolutions";

  private final static String BALISE_RESOLUTION = "resolution";

  private final static String BALISE_POULE = "poule";

  private final static String BALISE_JOUEUR = "joueur";

  private final static String BALISE_PRENOM = "prenom";

  private final static String BALISE_NOM = "nom";

  private final static String BALISE_POSTE = "poste";

  private final static String BALISE_CLUB = "club";

  private final static String BALISE_PA = "pa";

  private final static String BALISE_MV = "pa";

  private final static String BALISE_EKYP = "ekyp";

  private final static String BALISE_MONTANT = "montant";

  private final static String BALISE_RESULTAT = "resultat";

  private final static String BALISE_OFFRES = "offres";

  private final static String BALISE_OFFRE = "offre";

  // Déclaration des attributs
  private final static String ATTR_ID = "id";

  private final static String ATTR_TYPE = "type";

  private final static String ATTR_MONTANT = "montant";

  private final static String ATTR_EXCLUE = "exclue";

  private final static String ATTR_RESERVE = "reservePasAtteint";

  private final static String ATTR_PASDOFFRE = "pasDOffre";

  /**
   * Constructeur : l'encodage est toujours UTF-8 pour ce flux.
   */
  public ResolutionFeedParser()
  {
    super(ENCODING);
  }

  @Override
  protected List<Resolution> readXml(XmlPullParser pXmlPullParser) throws FeedParserException
  {
    List<Resolution> output = new ArrayList<Resolution>();

    Resolution currentResolution = null;
    Joueur currentJoueur = null;
    Club currentClub = null;
    Ekyp currentAuteur = null;
    Ekyp currentVainqueur = null;
    float currentMontantAuteur = 0;
    float currentMontantVainqueur = 0;
    List<Offre> currentOffres = null;
    int currentResolutionId = 0;
    String currentResolutionType = null;
    String currentPrenomJoueur = null;
    String currentNomJoueur = null;
    String currentPosteJoueur = null;
    float currentOffreMontant = 0;
    boolean currentOffreRejetee = false;
    boolean hasValidOffer = false;

    boolean isAuteurSection = false;
    boolean isResultatSection = false;
    String currentBalise = null;
    int eventType;
    try
    {
      eventType = pXmlPullParser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT)
      {
        if (eventType == XmlPullParser.START_DOCUMENT)
        {
          System.out.println("Start document");
        }
        else if (eventType == XmlPullParser.END_DOCUMENT)
        {
          System.out.println("End document");
        }
        else if (eventType == XmlPullParser.START_TAG)
        {
          // System.out.println("Start tag " + pXmlPullParser.getName());
          currentBalise = pXmlPullParser.getName();
          if (pXmlPullParser.getName().equals(BALISE_RESOLUTION))
          {
            currentResolutionId = Integer.parseInt(pXmlPullParser.getAttributeValue(0));
            currentResolutionType = pXmlPullParser.getAttributeValue(1);
          }
          else if (pXmlPullParser.getName().equals(BALISE_PA) || pXmlPullParser.getName().equals(BALISE_MV))
          {
            isAuteurSection = true;
          }
          else if (pXmlPullParser.getName().equals(BALISE_RESULTAT))
          {
            isResultatSection = true;
          }
          else if (pXmlPullParser.getName().equals(BALISE_OFFRES))
          {
            currentOffres = new ArrayList<Offre>();
          }
          else if (BALISE_OFFRE.equals(pXmlPullParser.getName()))
          {
            currentOffreMontant = Float.parseFloat(pXmlPullParser.getAttributeValue(0));
            currentOffreRejetee = Integer.parseInt(pXmlPullParser.getAttributeValue(1)) > 0;
            if (!currentOffreRejetee)
            {
              hasValidOffer = true;
            }
          }
        }
        else if (eventType == XmlPullParser.END_TAG)
        {
          // System.out.println("End tag " + pXmlPullParser.getName());
          if (BALISE_JOUEUR.equals(pXmlPullParser.getName()))
          {
            currentJoueur = new Joueur(currentNomJoueur, currentPrenomJoueur, currentPosteJoueur, currentClub);
          }
          else if (pXmlPullParser.getName().equals(BALISE_PA) || pXmlPullParser.getName().equals(BALISE_MV))
          {
            isAuteurSection = false;
          }
          else if (pXmlPullParser.getName().equals(BALISE_RESULTAT))
          {
            isResultatSection = false;
          }
          else if (pXmlPullParser.getName().equals(BALISE_OFFRE))
          {
            currentOffres.add(new Offre(currentOffreMontant, currentOffreRejetee));
          }
          else if (pXmlPullParser.getName().equals(BALISE_RESOLUTION))
          {
            if (currentAuteur == null)
            {
              currentAuteur = new Ekyp("pb construction flux");
            }
            currentResolution =
              new Resolution(currentResolutionId, currentJoueur, currentAuteur, new Date() /* TODO */,
                currentResolutionType, currentMontantAuteur, currentVainqueur, currentMontantVainqueur,
                new Date() /* TODO */, !hasValidOffer, currentOffres, false);
            output.add(currentResolution);
            // RAZ de tous les items du parsing:
            currentResolution = null;
            currentJoueur = null;
            currentClub = null;
            currentAuteur = null;
            currentVainqueur = null;
            currentMontantAuteur = 0;
            currentMontantVainqueur = 0;
            currentOffres = null;
            currentResolutionId = 0;
            currentResolutionType = null;
            currentPrenomJoueur = null;
            currentNomJoueur = null;
            currentPosteJoueur = null;
            currentOffreMontant = 0;
            currentOffreRejetee = false;
            hasValidOffer = false;
            isAuteurSection = false;
            isResultatSection = false;
            currentBalise = null;
          }
        }
        else if (eventType == XmlPullParser.TEXT)
        {
          // System.out.println("Text " + pXmlPullParser.getText());
          if (BALISE_CLUB.equals(currentBalise))
          {
            currentClub = new Club(pXmlPullParser.getText());
          }
          else if (BALISE_PRENOM.equals(currentBalise))
          {
            currentPrenomJoueur = pXmlPullParser.getText();
          }
          else if (BALISE_NOM.equals(currentBalise))
          {
            currentNomJoueur = pXmlPullParser.getText();
          }
          else if (BALISE_POSTE.equals(currentBalise))
          {
            currentPosteJoueur = pXmlPullParser.getText();
          }
          else if (BALISE_EKYP.equals(currentBalise))
          {
            if (isAuteurSection)
            {
              currentAuteur = new Ekyp(pXmlPullParser.getText());
            }
            if (isResultatSection)
            {
              currentVainqueur = new Ekyp(pXmlPullParser.getText());
            }
          }
          else if (BALISE_MONTANT.equals(currentBalise))
          {
            if (isAuteurSection)
            {
              currentMontantAuteur = Float.parseFloat(pXmlPullParser.getText());
            }
            if (isResultatSection)
            {
              currentMontantVainqueur = Float.parseFloat(pXmlPullParser.getText());
            }
          }
        }
        eventType = pXmlPullParser.next();
      }
    }
    catch (XmlPullParserException e)
    {
      throw new FeedParserException(e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new FeedParserException(e.getMessage(), e);
    }
    return output;
  }

}
