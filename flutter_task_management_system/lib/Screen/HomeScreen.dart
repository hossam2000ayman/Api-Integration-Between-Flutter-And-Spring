import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/Screen/AddTaskScreen.dart';
import 'package:flutter_task_management_system/Screen/GooglCalenderScreen.dart';
import 'package:flutter_task_management_system/Services/Database_Service.dart';
import 'package:flutter_task_management_system/TaskTile.dart';
import 'package:flutter_task_management_system/model/Task.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  List<Task>? tasks;

  getTasks() async {
    tasks = await DatabaseService.getTasks();
    Provider.of<TasksData>(context, listen: false).tasks = tasks!;
    setState(() {});
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getTasks();
  }

  @override
  Widget build(BuildContext context) {
    return tasks == null
        ? const Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          )
        : Scaffold(
            appBar: AppBar(
              actions: [
                Padding(
                  padding: EdgeInsets.all(10.0),
                  child: IconButton(
                    icon: Icon(Icons.calendar_month_sharp),
                    color: Colors.white,
                    onPressed: () {
                      Navigator.of(context).push(MaterialPageRoute(
                        builder: (context) => GoogleCalenderScreen(),
                      ));
                    },
                  ),
                ),
              ],
              title: Text(
                'Todo Task (${Provider.of<TasksData>(context).tasks.length})',
                style: TextStyle(
                  color: Colors.white,
                ),
              ),
              centerTitle: true,
              backgroundColor: Colors.green,
            ),
            body: Container(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Consumer<TasksData>(
                builder: (context, tasksData, child) {
                  return ListView.builder(
                    itemCount: tasksData.tasks.length,
                    itemBuilder: (context, index) {
                      Task task = tasksData.tasks[index];
                      return TaskTile(
                        task: task,
                        tasksData: tasksData,
                      );
                    },
                  );
                },
              ),
            ),
            floatingActionButton: FloatingActionButton(
              backgroundColor: Colors.green,
              onPressed: () {
                showModalBottomSheet(
                  context: context,
                  builder: (context) {
                    return AddTaskScreen();
                  },
                );
              },
              child: Icon(Icons.add,color: Colors.white,),
            ),
          );
  }
}
