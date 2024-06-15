package batnav.automated;

import batnav.automated.variant.AutomationGenerator;
import batnav.instance.Game;
import batnav.utils.Sambayon;

import java.util.Scanner;

public class StartAutomated
{
   public static void main(String[] args)
   {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Ingresá qué estrategia de ataque elegir: ");

      System.out.println("""
           1) Cruz
           2) Random
           3) Cruz con preferencia
           4) Random con preferencia.\s
          \s""");

      int a = scanner.nextInt();

      AutomationGenerator.AttackType attack = switch (a)
      {
         case 1 -> AutomationGenerator.AttackType.CROSS;
         case 2 -> AutomationGenerator.AttackType.RANDOM;
         case 3 -> AutomationGenerator.AttackType.CROSS_PREF;
         case 4 -> AutomationGenerator.AttackType.RANDOM_PREF;
         default -> null;
      };

      System.out.println("Ingresá qué puerto vas a usar:");
      int p = scanner.nextInt();
      Sambayon.setPort(p);

      System.out.println("Ingresá qué número de usuario elegir:");
      int u = scanner.nextInt();

      Game.getInstance().launch(AutomationGenerator.generate("usuario" + u, AutomationGenerator.DefenceType.RANDOM, attack));
   }
}
