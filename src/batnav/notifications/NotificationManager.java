package batnav.notifications;

import com.google.common.collect.Lists;

import java.util.List;

public class NotificationManager
{
   private final List<Notification> notificationList;

   /**
    * Manages everything notification related.
    */
   public NotificationManager()
   {
      this.notificationList = Lists.newArrayList();
   }

   /**
    * Adds a notification to the list.
    *
    * @param notification Notification object.
    */
   public void addNotification(final Notification notification)
   {
      this.notificationList.add(notification);
   }

   /**
    * Removes a notification from the list.
    *
    * @param notification Notification object.
    */
   public void removeNotification(final Notification notification)
   {
      this.notificationList.remove(notification);
   }

   public List<Notification> getNotifications()
   {
      return notificationList;
   }
}
