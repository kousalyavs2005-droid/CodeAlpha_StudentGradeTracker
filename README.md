# CodeAlpha_StudentGradeTracker

A professional Java Swing desktop application built for the CodeAlpha "Task 1: Student Grade Tracker" internship assignment.

## Assignment requirements covered
- Input and manage student grades
- Calculate highest and lowest scores
- Store and manage students using `ArrayList<Student>`
- Display a summary/performance report
- GUI-based interface

## Technology
- Java 17+
- Java Swing
- OOP
- MVC + Service + Repository architecture
- No external libraries or database required

## Project structure

```text
CodeAlpha_StudentGradeTracker/
├── src/
│   └── com/
│       └── studentgradetracker/
│           ├── Main.java                    # entry point
│           ├── model/
│           │   ├── Student.java
│           │   └── StudentStatistics.java
│           ├── repository/
│           │   └── StudentRepository.java   # in-memory ArrayList<Student> store
│           ├── service/
│           │   ├── StudentService.java      # add/update/delete/search + demo data
│           │   ├── GradeService.java        # total, average, grade calculation
│           │   └── StatisticsService.java   # class-wide statistics
│           ├── controller/
│           │   └── AppController.java       # links views to services
│           ├── view/
│           │   ├── LoginFrame.java
│           │   ├── DashboardFrame.java      # sidebar + topbar shell
│           │   ├── DashboardPanel.java
│           │   ├── AddStudentPanel.java
│           │   ├── StudentRecordsPanel.java
│           │   ├── SearchPanel.java
│           │   ├── PerformanceReportPanel.java
│           │   └── StatCard.java
│           └── util/
│               ├── AppColors.java           # dark navy / purple / cyan palette
│               ├── UIHelper.java            # shared styled components
│               ├── ValidationUtil.java
│               └── Constants.java
├── .gitignore
├── LICENSE
├── README.md
├── ARCHITECTURE.md
├── run.bat                                  # Windows
└── run.sh                                   # macOS / Linux
```

## Login
- Username: `admin`
- Password: `admin123`

## Run from VS Code / terminal

From the project root:

### Windows
```bat
run.bat
```

### macOS / Linux
```bash
./run.sh
```

### Manual compilation (Windows)
```bat
mkdir out
javac -d out src\com\studentgradetracker\Main.java src\com\studentgradetracker\model\*.java src\com\studentgradetracker\repository\*.java src\com\studentgradetracker\service\*.java src\com\studentgradetracker\controller\*.java src\com\studentgradetracker\view\*.java src\com\studentgradetracker\util\*.java
java -cp out com.studentgradetracker.Main
```

### Manual compilation (macOS / Linux)
```bash
mkdir -p out
javac -d out src/com/studentgradetracker/Main.java src/com/studentgradetracker/model/*.java src/com/studentgradetracker/repository/*.java src/com/studentgradetracker/service/*.java src/com/studentgradetracker/controller/*.java src/com/studentgradetracker/view/*.java src/com/studentgradetracker/util/*.java
java -cp out com.studentgradetracker.Main
```

## Features
- Professional dark navy / purple / cyan interface
- Login screen
- Dashboard statistics
- Add student
- Automatic total, average and grade calculation
- Student records table
- Search
- Update
- Delete
- Performance report
- Grade distribution
- Highest / lowest student
- Pass / fail count
- Responsive card-based Swing layout
- Sample data for immediate demonstration

## Grade scale
- A+ : 90–100
- A  : 80–89
- B  : 70–79
- C  : 60–69
- D  : 50–59
- F  : below 50

You can change the scale in `GradeService.java`.

## License
Released under the [MIT License](LICENSE).
