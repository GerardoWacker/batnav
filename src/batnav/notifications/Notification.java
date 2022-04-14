package batnav.notifications;

import java.util.function.Consumer;

public class Notification
{
   private final Priority priority;
   private final String title, description;
   private final Consumer<Boolean> consumer;

   /**
    * Creates a new Notification object
    *
    * @param priority    Priority for the notification.
    * @param title       Notification title to display.
    * @param description Description, if it's expanded.
    * @param consumer    Action to perform if the Notification is clicked.
    */
   public Notification(final Priority priority, final String title, final String description, final Consumer<Boolean> consumer)
   {
      this.priority = priority;
      this.title = title;
      this.description = description;
      this.consumer = consumer;
   }

   public Priority getPriority()
   {
      return priority;
   }

   public String getTitle()
   {
      return title;
   }

   public String getDescription()
   {
      return description;
   }

   public Consumer<Boolean> getConsumer()
   {
      return consumer;
   }

   /**
    * Notification priority. Defines order and importance.
    */
   public enum Priority
   {
      NOTICE, SOCIAL, WARNING, ALERT, CRITICAL
   }
}
