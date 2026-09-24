package com.studentgradetracker;

import javax.swing.SwingUtilities;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.repository.StudentRepository;
import com.studentgradetracker.service.GradeService;
import com.studentgradetracker.service.StatisticsService;
import com.studentgradetracker.service.StudentService;
import com.studentgradetracker.view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRepository repository = new StudentRepository();
            GradeService gradeService = new GradeService();
            StudentService studentService = new StudentService(repository, gradeService);
            StatisticsService statisticsService = new StatisticsService(gradeService);

            AppController controller =
                    new AppController(studentService, statisticsService);

            new LoginFrame(controller).setVisible(true);
        });
    }
}
