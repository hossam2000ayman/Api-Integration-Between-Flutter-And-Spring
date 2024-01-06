import 'package:flutter/material.dart';
import 'package:flutter_task_management_system/model/Task.dart';
import 'package:flutter_task_management_system/model/Tasks_data.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import 'package:syncfusion_flutter_calendar/calendar.dart';

class GoogleCalenderScreen extends StatefulWidget {
  const GoogleCalenderScreen({super.key});

  @override
  State<GoogleCalenderScreen> createState() => GoogleCalenderScreenState();
}

class GoogleCalenderScreenState extends State<GoogleCalenderScreen> {
  @override
  Widget build(BuildContext context) {

List<Appointment> getTasks() {
  List<Appointment> appointments = [];
  List<Task> tasks = Provider.of<TasksData>(context).tasks;

  //convert task to appointments
  for (Task task in tasks) {
    DateTime startDate = DateFormat("yyyy.MM.dd").parse(task.startDate);
    DateTime endDate = startDate.add(Duration(hours: task.durationInHour));

    appointments.add(Appointment(
      startTime: startDate,
      endTime: endDate,
      subject: task.title,
      notes: task.description,
      color: task.done ? Colors.grey : Colors.blue,
    ));
    }
  return appointments;
}




    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        title: Text(
          "Google Calendar",
          style: TextStyle(color: Colors.white),
        ),
        centerTitle: true,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.white,
          ),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ),
      body: SfCalendar(
        view: CalendarView.week,
        firstDayOfWeek: 6,
        initialDisplayDate: DateTime.now(),
        initialSelectedDate: DateTime.now(),
        dataSource: MeetingDataSource(getTasks()),
      ),
    );
  }
}



class MeetingDataSource extends CalendarDataSource {
  MeetingDataSource(List<Appointment> source) {
    appointments = source;
  }
}
