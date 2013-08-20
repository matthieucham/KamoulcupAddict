package com.kamoulcup.mobile.addict.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Utilitaire de formattage d'un montant en kamouls.
 */
public final class ResolutionUtils
{

  private ResolutionUtils()
  {
  }

  /**
   * A appeler à chaque fois que l'on veut afficher un montant. Plus facile à maintenir.
   */
  public static final String formatAmount(float amount)
  {
    NumberFormat formatter = NumberFormat.getInstance();
    formatter.setMaximumFractionDigits(1);
    formatter.setMinimumFractionDigits(1);
    formatter.setRoundingMode(RoundingMode.HALF_UP);
    return formatter.format(amount) + " Ka";
  }

  /**
   * Pour savoir si cette vente est une PA.
   */
  public static boolean isPA(String typeVente)
  {
    return "PA".equalsIgnoreCase(typeVente);
  }

}
