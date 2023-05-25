package task3;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TasksChecking {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void viewOpenTasks(int userId) {
        try {
            String tasksUrl = BASE_URL + "/users/" + userId + "/todos";
            String tasksJson = fetchData(tasksUrl);

            System.out.println("Не выполненые задачи пользователя " + userId + ":");
            printOpenTasks(tasksJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fetchData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder response = new StringBuilder();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, bytesRead));
        }
        inputStream.close();

        return response.toString();
    }

    private static void printOpenTasks(String tasksJson) {
        Gson gson = new GsonBuilder().create();
        Task[] tasks = gson.fromJson(tasksJson, Task[].class);

        for (Task task : tasks) {
            if (!task.isCompleted()) {
                System.out.println("Task ID: " + task.getId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Completed: " + task.isCompleted());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        viewOpenTasks(1);
    }

    public static class Task {
        private int id;
        private int userId;
        private String title;
        private boolean completed;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}


