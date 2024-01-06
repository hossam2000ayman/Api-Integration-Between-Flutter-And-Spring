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

  DateTime? startDate = DateFormat("yyyy.MM.dd").parse("2024.01.01");
  int hour = 0;
  int minute = 0;
  int second = 0;
  int durationInHour = 0;
  String location = "";

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
            height: 20,
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
            height: 30,
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
            height: 30,
          ),
          Text(
            'Add Time (24 hr)',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 30,
              color: Colors.green,
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Container(
                width: 100,
                child: TextField(
                  decoration: InputDecoration(labelText: "Hour"),
                  autofocus: true,
                  onChanged: (val) {
                    hour = int.parse(val);
                  },
                ),
              ),
              Container(
                width: 100,
                child: TextField(
                  decoration: InputDecoration(labelText: "Minute"),
                  autofocus: true,
                  onChanged: (val) {
                    minute = int.parse(val);
                  },
                ),
              ),
              Container(
                width: 100,
                child: TextField(
                  decoration: InputDecoration(labelText: "Second"),
                  autofocus: true,
                  onChanged: (val) {
                    second = int.parse(val);
                  },
                ),
              ),
            ],
          ),
          SizedBox(
            height: 5,
          ),
          Container(
            width: 100,
            margin: EdgeInsets.symmetric(horizontal: 100),
            child: TextField(
              decoration: InputDecoration(labelText: "Duration In Hour"),
              autofocus: true,
              onChanged: (val) {
                durationInHour = int.parse(val);
              },
            ),
          ),
          SizedBox(
            height: 20,
          ),
          Text(
            'Add Location',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 30,
              color: Colors.green,
            ),
          ),
          TextField(
            autofocus: true,
            onChanged: (val) {
              location = val;
            },
          ),
          SizedBox(
            height: 40,
          ),
          TextButton(
            onPressed: () {
              if (taskTitle.isNotEmpty &&
                  taskDescription.isNotEmpty &&
                  hour <= 23 &&
                  minute <= 60 &&
                  second <= 60 &&
                  durationInHour.isFinite &&
                  !durationInHour.isNaN &&
                  location.isNotEmpty) {
                Provider.of<TasksData>(context, listen: false).addTask(
                    taskTitle,
                    taskDescription,
                    formattedDate,
                    hour,
                    minute,
                    second,
                    durationInHour,
                    location);
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
