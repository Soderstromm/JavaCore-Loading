import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static String zipPath = "C:\\Games\\savegames\\saves.zip";
    private static String outputFolder = "C:\\Games\\savegames";
    private static List<String> saveFiles = Arrays.asList(
            outputFolder + "\\save1.dat",
            outputFolder + "\\save2.dat",
            outputFolder + "\\save3.dat"
    );

    public static void main(String[] args) {
        //1. Открытие архива
        openZip(zipPath, outputFolder);

        //2. Получение данных из сохранения
        GameProgress gameProgress = LoadProgress.openProgress(saveFiles.get(1));
        gameProgress.toString();

        if (gameProgress != null) {
            System.out.println("Состояние сохраненной игры:");
            System.out.println(gameProgress);
        } else {
            System.out.println("Не удалось загрузить сохранение.");
        }
    }

    //Метод распаковки
    public static void openZip(String zipPath, String outputFolder) {
        try (FileInputStream fis = new FileInputStream(zipPath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = outputFolder + "\\" + entry.getName();
                System.out.println("Распаковка: " + fileName);

                try (FileOutputStream fos = new FileOutputStream(fileName)) {
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
                zis.closeEntry();
            }
            System.out.println("Архив успешно распакован.");
        } catch (IOException e) {
            System.out.println("Ошибка при распаковке архива: " + e.getMessage());
        }
    }
}