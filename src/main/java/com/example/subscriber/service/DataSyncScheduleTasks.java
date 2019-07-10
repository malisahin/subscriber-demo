package com.example.subscriber.service;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public interface DataSyncScheduleTasks {

   void scheduleTaskWithFixedRate();

   void scheduleTaskWithFixedDelay();

   void scheduleTaskWithInitialDelay();

   void scheduleTaskWithCronExpression();
}
