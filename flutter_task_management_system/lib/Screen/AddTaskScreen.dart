import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class AddTaskScreen extends StatefulWidget {
  const AddTaskScreen({super.key});

  @override
  State<AddTaskScreen> createState() => _AddTaskScreenState();
}

class _AddTaskScreenState extends State<AddTaskScreen> {
  String taskTitle = "";
  String taskDescription = "";

  DateTime? startDate = DateFormat("yyyy.MM.dd").parse("2024.01.03");

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: startDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );

    if (picked != null && picked != startDate) {
      setState(() {
        startDate = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    String formattedDate = DateFormat("yyyy.MM.dd").format(startDate!);

    return Container(
      padding: EdgeInsets.all(20),
      child: ListView(
        children: [
          Text(
            'Add Task',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 30,
              color: Colors.green,
            ),
          ),
          TextField(
            autofocus: true,
            onChanged: (val) {
              taskTitle = val;
            },
          ),
          SizedBox(
            height: 40,
          ),
          Text(
            'Add Description',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 30,
              color: Colors.green,
            ),
          ),
          TextField(
            autofocus: true,
            onChanged: (val) {
              taskDescription = val;
            },
          ),
          SizedBox(
            height: 40,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                padding: EdgeInsets.symmetric(
                  vertical: 10,
                  horizontal: 20,
                ),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10),
                    color: Colors.green[700]),
                child: Text(
                  startDate == null
                      ? 'No date selected'
                      : 'Selected Date: ${formattedDate}',
                  style: TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                  ),
                ),
              ),
            ],
          ),
          SizedBox(
            height: 20,
          ),
          ElevatedButton(
            onPressed: () => _selectDate(context),
            child: Text(
              'Select Start Date',
              style: TextStyle(
                color: Colors.white,
              ),
            ),
            style: ButtonStyle(
              backgroundColor: MaterialStateProperty.all(Colors.green[300]),
            ),
          ),
          SizedBox(
            height: 40,
          ),
          TextButton(
            onPressed: () {
              if (taskTitle.isNotEmpty && taskDescription.isNotEmpty) {
                Provider.of<TasksData>(context, listen: false)
                    .addTask(taskTitle, taskDescription, formattedDate);
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
