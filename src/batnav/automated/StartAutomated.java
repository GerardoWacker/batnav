package batnav.automated;

import batnav.automated.variant.AutomationGenerator;
import batnav.instance.Game;

import java.util.Scanner;

public class StartAutomated
{
   public static void main(String[] args)
   {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Ingresá número de usuario: ");
      int i = scanner.nextInt();
      Game.getInstance().launch(AutomationGenerator.generate("usuario" + i,
              AutomationGenerator.DefenceType.RANDOM, AutomationGenerator.AttackType.RANDOM_PREF));
   }
}
