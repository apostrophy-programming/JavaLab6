package lab.io;

import lab.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Отвечает за загрузку коллекции из XML-файла и сохранение в XML.
 * Для чтения используется Scanner, для записи – BufferedOutputStream.
 */
public class FileManager {
    private final String fileName;

    /**
     * Конструктор.
     *
     * @param fileName имя файла для загрузки/сохранения
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Загружает коллекцию из XML-файла.
     * @return {@code PriorityQueue<Vehicle>}
     * @throws IOException если файл не найден или нет прав
     */
    public PriorityQueue<Vehicle> loadFromFile() throws IOException {
        PriorityQueue<Vehicle> collection = new PriorityQueue<>();
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не существует. Будет создана пустая коллекция.");
            return collection;
        }
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder xml = new StringBuilder();
            while (scanner.hasNextLine()) {
                xml.append(scanner.nextLine()).append("\n");
            }
            String content = xml.toString();
            int startIdx = 0;
            while (true) {
                int openTag = content.indexOf("<vehicle>", startIdx);
                if (openTag == -1) break;
                int closeTag = content.indexOf("</vehicle>", openTag);
                if (closeTag == -1) break;
                String vehicleBlock = content.substring(openTag + "<vehicle>".length(), closeTag);
                Vehicle vehicle = parseVehicle(vehicleBlock);
                if (vehicle != null) {
                    collection.add(vehicle);
                }
                startIdx = closeTag + "</vehicle>".length();
            }
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage());
        }
        return collection;
    }

    private Vehicle parseVehicle(String block) {
        try {
            Long id = Long.parseLong(extractTag(block, "id"));
            String name = extractTag(block, "name");
            Integer x = Integer.parseInt(extractTag(block, "x"));
            Long y = Long.parseLong(extractTag(block, "y"));
            LocalDate creationDate = LocalDate.parse(extractTag(block, "creationDate"));
            Integer enginePower = extractTag(block, "enginePower").isEmpty() ? null : Integer.parseInt(extractTag(block, "enginePower"));
            float capacity = Float.parseFloat(extractTag(block, "capacity"));
            VehicleType type = VehicleType.valueOf(extractTag(block, "type"));
            String fuelStr = extractTag(block, "fuelType");
            FuelType fuelType = fuelStr.isEmpty() ? null : FuelType.valueOf(fuelStr);

            Coordinates coordinates = new Coordinates(x, y);
            Vehicle vehicle = new Vehicle(id, name, coordinates, creationDate, enginePower, capacity, type, fuelType);
            return vehicle;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга элемента: " + e.getMessage());
            return null;
        }
    }


    private String extractTag(String block, String tag) {
        String open = "<" + tag + ">";
        String close = "</" + tag + ">";
        int start = block.indexOf(open);
        if (start == -1) return "";
        start += open.length();
        int end = block.indexOf(close, start);
        if (end == -1) return "";
        return block.substring(start, end).trim();
    }


    /**
     * Сохраняет коллекцию в XML-файл.
     * @param collection коллекция для сохранения
     * @throws IOException при ошибках записи
     */
    public void saveToFile(PriorityQueue<Vehicle> collection) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<vehicles>\n");
            for (Vehicle v : collection) {
                sb.append("  <vehicle>\n");
                sb.append("    <id>").append(v.getId()).append("</id>\n");
                sb.append("    <name>").append(escapeXml(v.getName())).append("</name>\n");
                sb.append("    <coordinates>\n");
                sb.append("      <x>").append(v.getCoordinates().getX()).append("</x>\n");
                sb.append("      <y>").append(v.getCoordinates().getY()).append("</y>\n");
                sb.append("    </coordinates>\n");
                sb.append("    <creationDate>").append(v.getCreationDate()).append("</creationDate>\n");
                sb.append("    <enginePower>").append(v.getEnginePower() == null ? "" : v.getEnginePower()).append("</enginePower>\n");
                sb.append("    <capacity>").append(v.getCapacity()).append("</capacity>\n");
                sb.append("    <type>").append(v.getType()).append("</type>\n");
                sb.append("    <fuelType>").append(v.getFuelType() == null ? "" : v.getFuelType()).append("</fuelType>\n");
                sb.append("  </vehicle>\n");
            }
            sb.append("</vehicles>");
            bos.write(sb.toString().getBytes("UTF-8"));
        }
    }

    private String escapeXml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}