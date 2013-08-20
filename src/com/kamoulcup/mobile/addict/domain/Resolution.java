package com.kamoulcup.mobile.addict.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Objet décrivant une résolution, produit par parsing XML.
 */
public class Resolution implements Parcelable
{
  /**
   * Id de la résolution.
   */
  private int _id;

  /**
   * Si cette résolution a été recalculée.
   */
  private boolean _updated;

  /**
   * Joueur concerné par l'enchère.
   */
  private Joueur _cible;

  /**
   * Auteur de la mise en vente.
   */
  private Ekyp _auteur;

  /**
   * Date de la mise en vente.
   */
  private Date _dateVente;

  /**
   * Type de mise en vente.
   */
  private String _typeVente;

  /**
   * Montant de la mise en vente.
   */
  private float _montantVente;

  /**
   * Ekyp gagnante de l'enchère.
   */
  private Ekyp _gagnant;

  /**
   * Montant de la meilleure offre valide qui a permis de remporter l'ench�re.
   */
  private float _montantGagnant;

  /**
   * Date de la résolution de l'enchère.
   */
  private Date _dateResolution;

  /**
   * Si la résolution a donné 0 offre valide (seulement pour les MV).
   */
  private boolean _noValidOffer;

  /**
   * Liste des offres reçues pour cette enchère.
   */
  private List<Offre> _offres;

  // ================================ Impl. Parcelable =================================================
  public static final Parcelable.Creator<Resolution> CREATOR = new Parcelable.Creator<Resolution>()
  {
    @Override
    public Resolution createFromParcel(Parcel source)
    {
      return new Resolution(source);
    }

    @Override
    public Resolution[] newArray(int size)
    {
      return new Resolution[size];
    }
  };

  public Resolution(Parcel in)
  {
    _id = in.readInt();
    _cible = (Joueur) in.readParcelable(null);
    _auteur = (Ekyp) in.readParcelable(null);
    _dateVente = new Date(in.readLong());
    _typeVente = in.readString();
    _montantVente = in.readFloat();
    _gagnant = (Ekyp) in.readParcelable(null);
    _montantGagnant = in.readFloat();
    _dateResolution = new Date(in.readLong());
    _noValidOffer = in.readByte() != 0;
    _offres = Arrays.asList((Offre[]) in.readParcelableArray(Offre.class.getClassLoader()));
    _updated = in.readByte() != 0;
  }

  /**
   * Constructeur complet.
   */
  public Resolution(int p_id, Joueur p_cible, Ekyp p_auteur, Date p_dateVente, String p_typeVente,
    float p_montantVente, Ekyp p_gagnant, float p_montantGagnant, Date p_dateResolution, boolean p_noValidOffer,
    List<Offre> p_offres, boolean p_updated)
  {
    _id = p_id;
    _cible = p_cible;
    _auteur = p_auteur;
    _dateVente = p_dateVente;
    _typeVente = p_typeVente;
    _montantVente = p_montantVente;
    _gagnant = p_gagnant;
    _montantGagnant = p_montantGagnant;
    _dateResolution = p_dateResolution;
    _noValidOffer = p_noValidOffer;
    _offres = p_offres;
    _updated = p_updated;
  }

  @Override
  public int describeContents()
  {
    return 4;
  }

  @Override
  public void writeToParcel(Parcel pDest, int pFlags)
  {
    pDest.writeInt(_id);
    pDest.writeParcelable(_cible, pFlags);
    pDest.writeParcelable(_auteur, pFlags);
    pDest.writeLong(_dateVente.getTime());
    pDest.writeString(_typeVente);
    pDest.writeFloat(_montantVente);
    pDest.writeParcelable(_gagnant, pFlags);
    pDest.writeFloat(_montantGagnant);
    pDest.writeLong(_dateResolution.getTime());
    pDest.writeByte((byte) (_noValidOffer
                                         ? 1 : 0));
    Offre[] offresArray = new Offre[_offres.size()];
    pDest.writeParcelableArray(_offres.toArray(offresArray), pFlags);
    pDest.writeByte((byte) (_updated
                                    ? 1 : 0));
  }

  public final Joueur getCible()
  {
    return _cible;
  }

  public final Ekyp getAuteur()
  {
    return _auteur;
  }

  public final Date getDateVente()
  {
    return _dateVente;
  }

  public final String getTypeVente()
  {
    return _typeVente;
  }

  public final float getMontantVente()
  {
    return _montantVente;
  }

  public final Ekyp getGagnant()
  {
    return _gagnant;
  }

  public final float getMontantGagnant()
  {
    return _montantGagnant;
  }

  public final Date getDateResolution()
  {
    return _dateResolution;
  }

  public final boolean isNoValidOffer()
  {
    return _noValidOffer;
  }

  public final List<Offre> getOffres()
  {
    return _offres;
  }

  public final int getId()
  {
    return _id;
  }

  public final boolean isUpdated()
  {
    return _updated;
  }

  @Override
  public boolean equals(Object r)
  {
    if (!(r instanceof Resolution))
    {
      return false;
    }
    return ((Resolution) r).getId() == getId() && ((Resolution) r).isUpdated() == isUpdated();
  }

  public float computeGap()
  {
    // Parcourir les offres pour identifier les deux plus hautes.
    float bestOffer = -1f;
    float secondBest = -1f;
    if (_offres != null)
    {
      for (Offre current : _offres)
      {
        if (!current.isRejetee())
        {
          if (current.getMontant() > bestOffer)
          {
            secondBest = bestOffer;
            bestOffer = current.getMontant();
          }
          else if (current.getMontant() > secondBest)
          {
            secondBest = current.getMontant();
          }
        }
      }
    }
    if (bestOffer > 0)
    {
      if (secondBest > 0)
      {
        return bestOffer - secondBest;
      }
      else
      {
        return bestOffer - _montantVente;
      }
    }
    else
    {
      return -1;
    }
  }

  // pour pas répandre les constantes partout.
  public boolean isPA()
  {
    return "PA".equalsIgnoreCase(_typeVente);
  }

  public boolean isMV()
  {
    return "MV".equalsIgnoreCase(_typeVente);
  }

  public boolean isRE()
  {
    return "RE".equalsIgnoreCase(_typeVente);
  }
}
