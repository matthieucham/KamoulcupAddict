/******************************************************************************
 * Projet   : Octaèdre
 * Société  : Devinlec
 ******************************************************************************/
/**
 * 
 */
package com.kamoulcup.mobile.addict.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Club auquel un joueur appartient.
 */
public class Club implements Parcelable
{

  /**
   * Définition du club spécial "Sansclub" a utiliser pour les joueurs sans club.
   */
  public static final Club SANS_CLUB = new Club("");

  /**
   * Nom du club.
   */
  private String _nom;

  // ================================ Impl. Parcelable =================================================
  public static final Parcelable.Creator<Club> CREATOR = new Parcelable.Creator<Club>()
  {
    @Override
    public Club createFromParcel(Parcel source)
    {
      return new Club(source);
    }

    @Override
    public Club[] newArray(int size)
    {
      return new Club[size];
    }
  };

  public Club(Parcel in)
  {
    _nom = in.readString();
  }

  public Club(String nom)
  {
    _nom = nom;
  }

  @Override
  public int describeContents()
  {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel pDest, int pFlags)
  {
    pDest.writeString(_nom);

  }

  public final String getNom()
  {
    return _nom;
  }

}
