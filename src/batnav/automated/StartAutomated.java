package batnav.automated;

import batnav.automated.variant.AutomationGenerator;
import batnav.instance.Game;

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

      System.out.println("Ingresá qué estrategia de defensa elegir: ");

      System.out.println("""
           1) Esquinas
           2) Random
           3) Centro\s
          \s""");

      int d = scanner.nextInt();

      AutomationGenerator.AttackType attack = switch (a)
      {
         case 1 -> AutomationGenerator.AttackType.CROSS;
         case 2 -> AutomationGenerator.AttackType.RANDOM;
         case 3 -> AutomationGenerator.AttackType.CROSS_PREF;
         case 4 -> AutomationGenerator.AttackType.RANDOM_PREF;
         default -> null;
      };

      AutomationGenerator.DefenceType defence = switch (d)
      {
         case 1 -> AutomationGenerator.DefenceType.CORNER;
         case 2 -> AutomationGenerator.DefenceType.RANDOM;
         case 3 -> AutomationGenerator.DefenceType.MIDDLE;
         default -> null;
      };

      System.out.println("Ingresá qué número de usuario elegir:");
      int u = scanner.nextInt();

      Game.getInstance().launch(AutomationGenerator.generate("usuario" + u, defence, attack));
   }
}
