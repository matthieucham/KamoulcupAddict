package com.kamoulcup.mobile.addict.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class Joueur implements Parcelable
{
  /**
   * Nom du Joueur.
   */
  private String _nom;

  /**
   * Prénom du Joueur.
   */
  private String _prenom;

  /**
   * Poste du joueur.
   */
  private String _poste;

  /**
   * Club du joueur.
   */
  private Club _club;

  // ================================ Impl. Parcelable =================================================
  public static final Parcelable.Creator<Joueur> CREATOR = new Parcelable.Creator<Joueur>()
  {
    @Override
    public Joueur createFromParcel(Parcel source)
    {
      return new Joueur(source);
    }

    @Override
    public Joueur[] newArray(int size)
    {
      return new Joueur[size];
    }
  };

  public Joueur(Parcel in)
  {
    _nom = in.readString();
    _prenom = in.readString();
    _poste = in.readString();
    _club = in.readParcelable(null);
  }

  public Joueur(String nom, String prenom, String poste, Club club)
  {
    _nom = nom;
    _prenom = prenom;
    _poste = poste;
    _club = club;
  }

  @Override
  public int describeContents()
  {
    return 1;
  }

  @Override
  public void writeToParcel(Parcel pDest, int pFlags)
  {
    pDest.writeString(_nom);
    pDest.writeString(_prenom);
    pDest.writeString(_poste);
    pDest.writeParcelable(_club, pFlags);
  }

  public final String getNom()
  {
    return _nom;
  }

  public final String getPrenom()
  {
    return _prenom;
  }

  public final String getPoste()
  {
    return _poste;
  }

  public final Club getClub()
  {
    return _club;
  }

}
