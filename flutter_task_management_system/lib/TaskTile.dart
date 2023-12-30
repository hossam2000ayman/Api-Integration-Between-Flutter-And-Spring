import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/model/Task.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';

class TaskTile extends StatelessWidget {
  final Task task;
  final TasksData tasksData;

  const TaskTile({Key? key, required this.task, required this.tasksData})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        leading: Checkbox(
          activeColor: Colors.green,
          value: task.done,
          onChanged: (checkbox) {
            tasksData.updateTask(task);
          },
        ),
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Text(
              task.title,
              style: TextStyle(
                fontSize: task.done ? 20 : 30,
                fontWeight: task.done ? FontWeight.normal : FontWeight.bold,
                decoration: task.done
                    ? TextDecoration.lineThrough
                    : TextDecoration.none,
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Container(
              color: task.done
                  ? Colors.transparent
                  : Colors.green.withOpacity(0.5),
              child: Text(
                task.description,
                style: TextStyle(
                  fontSize: task.done ? 10 : 15,
                  decoration: task.done
                      ? TextDecoration.lineThrough
                      : TextDecoration.none,
                ),
              ),
            ),
          ],
        ),
        trailing: IconButton(
          icon: Icon(Icons.clear),
          onPressed: () {
            tasksData.deleteTask(task);
          },
        ),
      ),
    );
  }
}
