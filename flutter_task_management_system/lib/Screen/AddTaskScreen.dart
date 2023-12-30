import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:provider/provider.dart';

class AddTaskScreen extends StatefulWidget {
  const AddTaskScreen({super.key});

  @override
  State<AddTaskScreen> createState() => _AddTaskScreenState();
}

class _AddTaskScreenState extends State<AddTaskScreen> {
  String taskTitle = "";
  String taskDescription = "";

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(20),
      child: ListView(
        children: [
          Text('Add Task',
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 30, color: Colors.green)),
          TextField(
            autofocus: true,
            onChanged: (val) {
              taskTitle = val;
            },
          ),
          SizedBox(
            height: 40,
          ),
          Text('Add Description',
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 30, color: Colors.green)),
          TextField(
            autofocus: true,
            onChanged: (val) {
              taskDescription = val;
            },
          ),
          SizedBox(
            height: 40,
          ),
          TextButton(
            onPressed: () {
              if (taskTitle.isNotEmpty && taskDescription.isNotEmpty) {
                Provider.of<TasksData>(context, listen: false)
                    .addTask(taskTitle, taskDescription);
              }
              Navigator.of(context).pop();
            },
            child: Text(
              'Add',
              style: TextStyle(color: Colors.white),
            ),
            style: TextButton.styleFrom(backgroundColor: Colors.green),
          ),
        ],
      ),
    );
  }
}
