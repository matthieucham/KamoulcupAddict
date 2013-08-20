package com.kamoulcup.mobile.addict.utils;

import java.util.Comparator;

import com.kamoulcup.mobile.addict.domain.Offre;

/**
 * Compare deux offres sans s'occuper de sa validité.
 */
public class OffreMontantComparator implements Comparator<Offre>
{

  public OffreMontantComparator()
  {
  }

  @Override
  public int compare(Offre pArg0, Offre pArg1)
  {
    return (Math.round(pArg0.getMontant() * 10) - Math.round(pArg1.getMontant() * 10));
  }

}
