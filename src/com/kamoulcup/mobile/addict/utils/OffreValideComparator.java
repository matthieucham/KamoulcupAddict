package com.kamoulcup.mobile.addict.utils;

import java.util.Comparator;

import com.kamoulcup.mobile.addict.domain.Offre;

/**
 * Compare 2 offres en considèrant qu'une valide est toujours supérieure à une invalide.
 */
public class OffreValideComparator implements Comparator<Offre>
{

  OffreMontantComparator _montantsComp;

  public OffreValideComparator()
  {
    _montantsComp = new OffreMontantComparator();
  }

  @Override
  public int compare(Offre pLhs, Offre pRhs)
  {
    if (pLhs.isRejetee())
    {
      if (pRhs.isRejetee())
      {
        // Comparaison sur les montants
        return _montantsComp.compare(pLhs, pRhs);
      }
      else
      {
        // pLhs est inférieure.
        return -1;
      }
    }
    else
    {
      if (pRhs.isRejetee())
      {
        // pLhs est supérieure.
        return 1;
      }
      else
      {
        // Comparaison sur les montants
        return _montantsComp.compare(pLhs, pRhs);
      }
    }
  }

}
