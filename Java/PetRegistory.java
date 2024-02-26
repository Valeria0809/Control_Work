package Java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.Counter;

import Java.Animal.Animals;
import Java.Animal.Camels;
import Java.Animal.Cats;
import Java.Animal.Dogs;
import Java.Animal.Donkeys;
import Java.Animal.Horses;
import Java.Animal.Humsters;

public class PetRegistory implements AutoCloseable {

    private List<Animals> animals = new ArrayList<>();

    private static Counter counter = new Counter();

    public void addNewAnimal(Animals animals) {
        animals.add(animals);
        counter.add();
    }

    public void teachCommand(Animals animal, String command) {
        animal.setCommand(command);

        try (FileWriter writer = new FileWriter("DataBase.csv", true)) {
            String animalType = getAnimalType(animal);
            String animalName = animal.getName();
            String line = animalType + "," + animalName + "," + command + "\n";
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAnimalType(Animals animal) {
        if (animal instanceof Dogs) {
            return "Dogs";
        } else if (animal instanceof Cats) {
            return "Cats";
        } else if (animal instanceof Humsters) {
            return "Humsters";
        } else if (animal instanceof Horses) {
            return "Horses";
        } else if (animal instanceof Camels) {
            return "Camels";
        } else if (animal instanceof Donkeys) {
            return "Donkeys";
        }
        return "";
    }

    public List<String> getCommands(Animals animal) {
        List<String> commands = new ArrayList<>();
        commands.add(animal.getCommand());
        return commands;
    }

    public void readDatabase() {
        File databaseFile = new File("DataBase.csv");
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String animalName = data[0];
                    String command = data[1];
                    Animals animal = animals.stream().filter(a -> a.getName().equals(animalName)).findFirst()
                            .orElse(null);
                    if (animal != null) {
                        animal.setCommand(command);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try (PetRegistory petRegistry = new PetRegistory()) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Add new animal");
                System.out.println("2. Teach command");
                System.out.println("3. Get commands");
                System.out.println("4. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter animal type: ");
                        String type = scanner.nextLine();
                        System.out.println("Enter animal name: ");
                        String name = scanner.nextLine();
                        Animals animal;
                        switch (type) {
                            case "Dogs":
                                animal = new Dogs(name);
                                break;
                            case "Cat":
                                animal = new Cats(name);
                                break;
                            case "Humsters":
                                animal = new Humsters(name);
                                break;
                            case "Horses":
                                animal = new Horses(name);
                                break;
                            case "Camels":
                                animal = new Camels(name);
                                break;
                            case "Donkeys":
                                animal = new Donkeys(name);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + type);
                        }
                        petRegistry.addNewAnimal(animal);
                        break;
                    case 2:
                        System.out.println("Enter animal name: ");
                        String animalName = scanner.nextLine();
                        Animals foundAnimal = petRegistry.animals.stream()
                                .filter(a -> a.getName().equals(animalName))
                                .findFirst()
                                .orElse(null);
                        if (foundAnimal == null) {
                            System.out.println("No such animal");
                            break;
                        }
                        System.out.println("Enter command: ");
                        String command = scanner.nextLine();
                        petRegistry.teachCommand(foundAnimal, command);
                        break;
                    case 3:
                        System.out.println("Enter animal name: ");
                        String aName = scanner.nextLine();
                        Animal fAnimal = petRegistry.animals.stream()
                                .filter(a -> a.getName().equals(aName))
                                .findFirst()
                                .orElse(null);
                        if (fAnimal == null) {
                            System.out.println("No such animal");
                            break;
                        }
                        List<String> commands = petRegistry.getCommands(fAnimal);
                        for (String cmd : commands) {
                            System.out.println(cmd);
                        }
                        break;
                    case 4:
                        return;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        if (counter.getCount() == 0) {
            throw new Exception("Counter was not used in try-with-resources block");
        } else {
            counter.resetCount();
        }
    }

    class Counter {

        private int count;

        public void add() {
            count++;
        }

        public int getCount() {
            return count;
        }

        public void resetCount() {
            count = 0;
        }
    }
}
