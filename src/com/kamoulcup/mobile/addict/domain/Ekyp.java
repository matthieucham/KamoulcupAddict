/******************************************************************************
 * Projet   : Octaèdre
 * Société  : Devinlec
 ******************************************************************************/
package com.kamoulcup.mobile.addict.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Ekyp qui enchérit ou qui poste la PA.
 */
public class Ekyp implements Parcelable
{

  /**
   * Nom de l'ékyp.
   */
  private String _nom;

  // ================================ Impl. Parcelable =================================================
  public static final Parcelable.Creator<Ekyp> CREATOR = new Parcelable.Creator<Ekyp>()
  {
    @Override
    public Ekyp createFromParcel(Parcel source)
    {
      return new Ekyp(source);
    }

    @Override
    public Ekyp[] newArray(int size)
    {
      return new Ekyp[size];
    }
  };

  public Ekyp(Parcel in)
  {
    _nom = in.readString();
  }

  public Ekyp(String nom)
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
