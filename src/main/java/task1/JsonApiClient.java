package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) {
        JsonApiClient apiClient = new JsonApiClient();

        String newUserJson = "{\"name\": \"Putin Hylo\", \"username\": \"putinhylo\"}";
        String createdUserJson = apiClient.createUser(newUserJson);
        System.out.println("Созданный пользователь: " + createdUserJson);

        String updatedUserJson = "{\"id\": 1, \"name\": \"Updated User\", \"username\": \"updatedusername\"}";
        apiClient.updateUser(updatedUserJson);

        boolean isDeleted = apiClient.deleteUser(1);
        System.out.println("Существует ли пользователь? " + isDeleted);

        String allUsersJson = apiClient.getAllUsers();
        System.out.println("Все пользователи: " + allUsersJson);

        String userByIdJson = apiClient.getUserById(3);
        System.out.println("Пользователь с id = 3: " + userByIdJson);

        String userByUsernameJson = apiClient.getUserByUsername("putinhylo");
        System.out.println("Пользователь с username: " + userByUsernameJson);
    }

    public String createUser(String userJson) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(userJson.getBytes());
            outputStream.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(String userJson) {
        try {
            URL url = new URL(BASE_URL + "/1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(userJson.getBytes());
            outputStream.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Обновленный пользователь: " + response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteUser(int userId) {
        try {
            URL url = new URL(BASE_URL + "/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
            return responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAllUsers() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserById(int userId) {
        try {
            URL url = new URL(BASE_URL + "/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserByUsername(String username) {
        try {
            URL url = new URL(BASE_URL + "?username=" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


