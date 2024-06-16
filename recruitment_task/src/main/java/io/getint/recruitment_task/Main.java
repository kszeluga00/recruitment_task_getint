package io.getint.recruitment_task;

public class Main {
    public static void main(String[] args) {
        JiraSynchronizer jiraSynchronizer = new JiraSynchronizer();
        try {
            jiraSynchronizer.moveTasksToOtherProject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
