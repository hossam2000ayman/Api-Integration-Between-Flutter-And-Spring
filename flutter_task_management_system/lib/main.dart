import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/Screen/HomeScreen.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:provider/provider.dart';

void main(){
  runApp(MyApp());
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<TasksData>(
      create: (context) => TasksData(),
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        home: HomeScreen(),
      ),
    );
  }
}
