import 'dart:convert';

import 'package:flutter_task_management_system/Services/globals.dart';
import 'package:flutter_task_management_system/model/Task.dart';
import 'package:http/http.dart' as http;

class DatabaseService {
  static Future<Task> addTask(
      String title, String description, String startDate) async {
    Map data = {
      "title": title,
      "description": description,
      "startDate": startDate
    };

    var body = json.encode(data);
    var url = Uri.parse(baseUrl + "/add");

    http.Response response = await http.post(url, headers: headers, body: body);
    print(response.body);
    Map responseMap = jsonDecode(response.body);

    Task task = Task.fromMap(responseMap);

    return task;
  }

  static Future<List<Task>> getTasks() async {
    var url = Uri.parse(baseUrl + "/read/all");
    http.Response response = await http.get(
      url,
      headers: headers,
    );
    print(response.body);
    List responseList = jsonDecode(response.body);
    List<Task> tasks = [];
    for (Map taskMap in responseList) {
      Task task = Task.fromMap(taskMap);
      tasks.add(task);
    }
    return tasks;
  }

  static Future<http.Response> updateTask(int id) async {
    var url = Uri.parse(baseUrl + "/update/${id}");
    http.Response response = await http.put(url, headers: headers);
    //Todo just display message
    print(response.body);
    return response;
  }

  static Future<http.Response> deleteTask(int id) async {
    var url = Uri.parse(baseUrl + "/delete/${id}");
    http.Response response = await http.delete(url, headers: headers);
    //Todo just display message
    print(response.body);
    return response;
  }
}
