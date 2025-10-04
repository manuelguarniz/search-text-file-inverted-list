import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PracticaListasInvertidas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Que palabra quieres buscar?:");
        String textToSearch = scanner.nextLine();
        searchWordInFiles(textToSearch);
    }

    private static void searchWordInFiles(String textToSearch) {

        Map<String, Set<String>> documents = new HashMap<>();
        Map<String, Set<String>> invertedList = new HashMap<>();

        File directory = new File("libros");
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos");
            return;
        }

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader("libros" + File.separator + file.getName()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (documents.containsKey(file.getName())) {
                        Set<String> currentLines = documents.get(file.getName());
                        currentLines.add(line);
                    } else {
                        documents.put(file.getName(), Set.of(line));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (String key : documents.keySet()) {
            String docName = key;
            Set<String> docContent = documents.get(key);

            for (String line : docContent) {
                String[] words = line.split("\\W+");

                for (String word : words) {
                    invertedList.computeIfAbsent(word, k -> new HashSet<>()).add(docName);
                }
            }
        }

        for (String key : invertedList.keySet()) {
            System.out.println("Palabra: " + key + " -> documentos: " + invertedList.get(key));
        }

        if (invertedList.containsKey(textToSearch)) {
            System.out.println("Se encontro la palabara '"+textToSearch+"' en los libros " + invertedList.get(textToSearch));
        }
    }
}
