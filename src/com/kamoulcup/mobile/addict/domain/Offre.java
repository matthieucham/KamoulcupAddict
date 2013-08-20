package com.kamoulcup.mobile.addict.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Offre d'enchère.
 */
public class Offre implements Parcelable
{

  /**
   * Montant proposé.
   */
  private float _montant;

  /**
   * Offre rejetee ?
   */
  private boolean _rejetee;

  // ================================ Impl. Parcelable =================================================
  public static final Parcelable.Creator<Offre> CREATOR = new Parcelable.Creator<Offre>()
  {
    @Override
    public Offre createFromParcel(Parcel source)
    {
      return new Offre(source);
    }

    @Override
    public Offre[] newArray(int size)
    {
      return new Offre[size];
    }
  };

  public Offre(Parcel in)
  {
    _montant = in.readFloat();
    _rejetee = in.readByte() != 0;
  }

  public Offre(float montant, boolean rejetee)
  {
    _montant = montant;
    _rejetee = rejetee;
  }

  @Override
  public int describeContents()
  {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel pDest, int pFlags)
  {
    pDest.writeFloat(_montant);
    pDest.writeByte((byte) (_rejetee
                                    ? 1 : 0));

  }

  public final float getMontant()
  {
    return _montant;
  }

  public final boolean isRejetee()
  {
    return _rejetee;
  }

}
