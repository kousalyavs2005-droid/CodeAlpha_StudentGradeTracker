# Architecture

The project uses a layered MVC-oriented architecture.

```text
View (Swing UI)
      |
      v
Controller
      |
      v
Service Layer
      |
      v
Repository
      |
      v
ArrayList<Student>
```

## Model
`Student` stores student data and calculated results.

## Repository
`StudentRepository` owns the `ArrayList<Student>` collection.

## Services
- `StudentService`: CRUD/search and coordination.
- `GradeService`: total, average and grade calculation.
- `StatisticsService`: highest/lowest, pass/fail and grade distribution.

## Controller
`AppController` exposes application operations to the Swing views.

## View
Swing frames/panels provide:
- Login
- Dashboard
- Add Student
- Student Records
- Search / Update / Delete
- Performance Report
