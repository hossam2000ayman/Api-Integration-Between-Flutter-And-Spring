class Task {
  final int id;
  final String title;
  final String description;
  bool done;
  String startDate;
  int hour;
  int minute;
  int second;
  int durationInHour;
  String location;

  Task(
      {required this.id,
      required this.title,
      required this.description,
      this.done = false,
      required this.startDate,
      required this.hour,
      required this.minute,
      required this.second,
      required this.durationInHour,
      required this.location});

  factory Task.fromMap(Map taskMap) {
    return Task(
      id: taskMap['id'],
      title: taskMap['title'],
      description: taskMap['description'],
      done: taskMap['done'],
      startDate: taskMap['startDate'],
      hour: taskMap['hour'],
      minute: taskMap['minute'],
      second: taskMap['second'],
      durationInHour: taskMap['durationInHour'],
      location: taskMap['location'],
    );
  }

  void toggle() {
    done = !done;
  }
}
