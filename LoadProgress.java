import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadProgress {
    public static GameProgress openProgress(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object object = ois.readObject();
            if (object instanceof GameProgress) {
                System.out.println("Файл успешно десериализован: " + filePath);
                return (GameProgress) object;
            } else {
                System.out.println("Некорректный формат файла: " + filePath);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при десериализации: " + e.getMessage());
        }
        return null;
    }
}
