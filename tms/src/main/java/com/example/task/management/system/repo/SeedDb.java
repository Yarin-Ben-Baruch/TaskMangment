//package com.example.task.management.system.repo;
//
//import com.example.task.management.system.enums.Priority;
//import com.example.task.management.system.enums.Status;
//import com.example.task.management.system.pojo.Task;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.time.LocalDate;
//import java.util.Random;
//
//@Slf4j
//@Configuration
//public class SeedDb {
//
//    private static int number = 1;
//
//    @Autowired private TaskRepository taskRepository;
//
//    @Scheduled(cron = " 0 * * * * *")
//    public void scheduleFixedDelayTask() {
//        int pickStatus = new Random().nextInt(Status.values().length);
//        int pickPriority = new Random().nextInt(Priority.values().length);
//        int pickDay = new Random().nextInt(30) + 1;
//
//        Task task = taskRepository.saveTask(Task.builder()
//                .name("Yarin" + number++)
//                .description("no description")
//                .priority(Priority.values()[pickPriority])
//                .startDate(LocalDate.now())
//                .expectedEndDate(LocalDate.of(2022, 06, pickDay))
//                .currentStatus(Status.values()[pickStatus])
//                .build());
//        log.info("task just create. task:{}", task);
//    }
//
//    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week]")
//    @Scheduled(cron = "0 0 14 * * MON")
//    public void scheduleFixedDelayTaskInFixDay() {
//        int pickStatus = new Random().nextInt(Status.values().length);
//        int pickPriority = new Random().nextInt(Priority.values().length);
//        int pickDay = new Random().nextInt(30) + 1;
//
//        Task task = taskRepository.saveTask(Task.builder()
//                .name("Yarin" + number++)
//                .description("no description")
//                .priority(Priority.values()[pickPriority])
//                .startDate(LocalDate.now())
//                .expectedEndDate(LocalDate.of(2022, 06, pickDay))
//                .currentStatus(Status.values()[pickStatus])
//                .build());
//        log.info("task just create. task:{}", task);
//    }
//
//    @Bean
//    public CommandLineRunner seedDatabase(TaskRepository taskRepository) {
//        return args -> {
//
//            Task task1 = taskRepository.saveTask(Task.builder()
//                    .name("Yarin2")
//                    .description("no description")
//                    .priority(Priority.MEDIUM)
//                    .startDate(LocalDate.of(2022, 05, 05))
//                    .expectedEndDate(LocalDate.of(2022, 05, 15))
//                    .currentStatus(Status.CLOSED)
//                    .build());
//            log.info("task just create. task:{}", task1);
//
//            Task task2 = taskRepository.saveTask(Task.builder()
//                    .name("Lior2")
//                    .description("no description")
//                    .priority(Priority.LOW)
//                    .startDate(LocalDate.of(2022, 05, 06))
//                    .expectedEndDate(LocalDate.of(2022, 05, 05))
//                    .currentStatus(Status.IN_PROCESS)
//                    .build());
//            log.info("task just create. task:{}", task2);
//
//            Task task3 = taskRepository.saveTask(Task.builder()
//                    .name("Dani2")
//                    .description("no description")
//                    .priority(Priority.LOW)
//                    .startDate(LocalDate.of(2022, 05, 07))
//                    .expectedEndDate(LocalDate.of(2022, 05, 21))
//                    .currentStatus(Status.OPEN)
//                    .build());
//            log.info("task just create. task:{}", task3);
//
//            Task task4 = taskRepository.saveTask(Task.builder()
//                    .name("Maor2")
//                    .description("no description")
//                    .priority(Priority.HIGH)
//                    .startDate(LocalDate.of(2022, 05, 8))
//                    .expectedEndDate(LocalDate.of(2022, 05, 20))
//                    .currentStatus(Status.OPEN)
//                    .build());
//            log.info("task just create. task:{}", task4);
//
//            Task task5 = taskRepository.saveTask(Task.builder()
//                    .name("Gal2")
//                    .description("no description")
//                    .priority(Priority.MEDIUM)
//                    .startDate(LocalDate.of(2022, 05, 9))
//                    .expectedEndDate(LocalDate.of(2022, 05, 19))
//                    .currentStatus(Status.OPEN)
//                    .build());
//            log.info("task just create. task:{}", task5);
//        };
//    }
//
////    @Bean
////    public Docket docket(){
////        return new Docket(DocumentationType.SWAGGER_2).select().build();
////    }
//}
