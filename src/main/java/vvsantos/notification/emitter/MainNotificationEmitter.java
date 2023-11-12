package vvsantos.notification.emitter;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

public class MainNotificationEmitter {

    public static void main(String[] args) throws InterruptedException {
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            if (!(gcBean instanceof NotificationEmitter notificationEmitter)) {
                continue;
            }
            System.out.println("NotificationEmitter" + gcBean.getName());
            notificationEmitter.addNotificationListener((notification, handback) -> {

                var type = notification.getType();

                System.out.printf("Notification  issued -> %s", type);
            }, notification -> notification.getType()
                    .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION), null);
        }
        System.out.println("Notifier added");
        System.gc();
        Thread.sleep(3_000);
        System.out.println("End");
    }

}
