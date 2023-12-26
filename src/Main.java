// файл LinksToYouTube.txt, в котором есть какие-то ссылки на YouTube, и есть еще один большой файл
// BigLinksToYouTube.txt с ссылками на YouTube. Используя регулярные выражения происходит проверка
// правильности ссылок в LinksToYouTube.txt. Неверно записанные ссылки выводятся на экран и
// удаляются из LinksToYouTube.txt. Затем идет проверка, есть ли в BigLinksToYouTube.txt оставшиеся
// в LinksToYouTube.txt правильные ссылки и выводится сообщение об этом на экран
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;   // поиск совпадений рег выражений
import java.util.regex.Pattern;  //создание рег выражений

public class Main {
    public static void main(String[] args) {
        // Путь к файлу с ссылками
        String filePath = "LinksToYouTube.txt";

        try {
            // Чтение файла с ссылками
            List<String> links = readLinksFile(filePath);

            // Проверка правильности ссылок и удаление неверных
            List<String> validLinks = validateLinks(links);

            // Путь к файлу с большими ссылками
            String bigLinksFilePath = "BigLinksToYouTube.txt";

            // Проверка наличия правильных ссылок в файле с большими ссылками
            boolean containsValidLinks = checkForValidLinks(bigLinksFilePath, validLinks);

            // Вывод сообщения о наличии/отсутствии правильных ссылок в файле с большими ссылками
            if (containsValidLinks) {
                System.out.println("Файл BigLinksToYouTube.txt содержит ссылки из файла LinksToYouTube.txt");
            } else {
                System.out.println("Файл BigLinksToYouTube.txt не содержит ссылки из файла LinksToYouTube.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для чтения файла с ссылками
    private static List<String> readLinksFile(String filePath) throws IOException {
        List<String> links = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                links.add(line);
            }
        }

        return links;
    }

    // Метод для проверки правильности ссылок и удаления неверных
    private static List<String> validateLinks(List<String> links) throws IOException {
        List<String> validLinks = new ArrayList<>();

        // Регулярное выражение для проверки ссылок на YouTube
        String regex = "^(https?\\:\\/\\/)?(www\\.)?youtube\\.com\\/watch\\?v=([a-zA-Z0-9_-]+)$";
        Pattern pattern = Pattern.compile(regex); //компилируем регулярное выражение в шаблон паттерн


        try (FileWriter writer = new FileWriter("LinksToYouTube.txt")) {
            for (String link : links) { //перебираем все в линкс
                Matcher matcher = pattern.matcher(link);  //ищем совпадения с рег выражением
                if (matcher.matches()) {
                    validLinks.add(link);
                    writer.write(link + "\n");
                } else {
                    System.out.println("Неверная ссылка: " + link);
                }
            }
        }

        return validLinks;
    }

    // Метод для проверки наличия правильных ссылок в файле с большими ссылками
    private static boolean checkForValidLinks(String bigLinksFilePath, List<String> validLinks) throws IOException {
        boolean containsValidLinks = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(bigLinksFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (validLinks.contains(line)) {
                    containsValidLinks = true;
                    System.out.println("Файл BigLinksToYouTube.txt содержит ссылку: " + line);
                }
            }
        }

        return containsValidLinks;
    }
}