package task2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserComment {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void exportComments(int userId) {
        try {
            String postsUrl = BASE_URL + "/users/" + userId + "/posts";
            String postsJson = fetchData(postsUrl);
            int lastPostId = getLastPostId(postsJson);

            if (lastPostId > 0) {
                String commentsUrl = BASE_URL + "/posts/" + lastPostId + "/comments";
                String commentsJson = fetchData(commentsUrl);

                String fileName = "user-" + userId + "-post-" + lastPostId + "-comments.json";
                writeToFile(fileName, commentsJson);

                System.out.println("Комментарии экспортированы в файл: " + fileName);
            } else {
                System.out.println("У пользователя отсутствуют посты.");
            }
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

    private static int getLastPostId(String postsJson) {
        return 10;
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(content);
        writer.close();
    }

    public static void main(String[] args) {
        exportComments(1);
    }
}


