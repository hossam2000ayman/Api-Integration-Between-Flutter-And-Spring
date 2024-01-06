import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/model/Task.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:intl/intl.dart';

class TaskTile extends StatelessWidget {
  final Task task;
  final TasksData tasksData;

  const TaskTile({Key? key, required this.task, required this.tasksData})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    String currentDate = DateFormat("yyyy.MM.dd").format(DateTime.now());
    String currentTime = DateFormat("HH:mm:ss").format(DateTime.now());
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
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    Text(
                      task.done
                          ? "Task ended at : ${currentDate}"
                          : "Task started at : ${task.startDate}",
                      style: TextStyle(
                        fontSize: task.done ? 13 : 15,
                        decoration: task.done
                            ? TextDecoration.lineThrough
                            : TextDecoration.none,
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        Text(
                          task.done
                              ? "from : ${task.hour}:${task.minute}:${task.second}"
                              : "from : ${task.hour}:${task.minute}:${task.second}",
                          style: TextStyle(
                            fontSize: task.done ? 13 : 15,
                            decoration: task.done
                                ? TextDecoration.lineThrough
                                : TextDecoration.none,
                          ),
                        ),
                        Text(
                          task.done
                              ? " to : ${task.hour + task.durationInHour}:${task.minute}:${task.second}"
                              : " to : ${task.hour + task.durationInHour}:${task.minute}:${task.second}",
                          style: TextStyle(
                            fontSize: task.done ? 13 : 15,
                            decoration: task.done
                                ? TextDecoration.lineThrough
                                : TextDecoration.none,
                          ),
                        ),
                      ],
                    ),
                    Text(
                      task.done
                          ? "Task is Done"
                          : "Estimating time : ${task.durationInHour} hours",
                      style: TextStyle(
                        fontSize: task.done ? 13 : 15,
                        decoration: task.done
                            ? TextDecoration.lineThrough
                            : TextDecoration.none,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
        trailing: IconButton(
          icon: Icon(Icons.delete),
          onPressed: () {
            tasksData.deleteTask(task);
          },
        ),
      ),
    );
  }
}
