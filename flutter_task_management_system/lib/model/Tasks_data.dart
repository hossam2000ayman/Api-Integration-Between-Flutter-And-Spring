import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/Services/Database_Service.dart';
import 'package:flutter_task_management_system/model/Task.dart';

class TasksData extends ChangeNotifier {
  List<Task> tasks = [];

  void addTask(String taskTitle, String taskDescription, String startDate) async {
    Task task = await DatabaseService.addTask(taskTitle, taskDescription,startDate);
    tasks.add(task);
    notifyListeners();
  }

  void updateTask(Task task) {
    task.toggle();
    DatabaseService.updateTask(task.id);
    notifyListeners();
  }

  void deleteTask(Task task) {
    tasks.remove(task);
    DatabaseService.deleteTask(task.id);
    notifyListeners();
  }
}
