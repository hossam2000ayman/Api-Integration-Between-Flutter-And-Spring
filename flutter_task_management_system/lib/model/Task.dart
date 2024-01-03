class Task {
  final int id;
  final String title;
  final String description;
  bool done;
  String startDate;

  Task({
    required this.id,
    required this.title,
    required this.description,
    this.done = false,
    required this.startDate,
  });

  factory Task.fromMap(Map taskMap) {
    return Task(
      id: taskMap['id'],
      title: taskMap['title'],
      description: taskMap['description'],
      done: taskMap['done'],
      startDate: taskMap['startDate'],
    );
  }

  void toggle() {
    done = !done;
  }
}
