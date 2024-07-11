import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome");
            Scanner scan = new Scanner(System.in);
            List<Todo> todoList = new ArrayList<>();
            todoList.add(new Todo());
            //파일에서 있는 데이터를 다시 todoList에 작성한다
            todoList = bufferedReader(todoList);
            //제목들을 갖는 todoTitles를 만들다
            List<String> todoTitles = new ArrayList<>();
            int index = 1;
            for (Todo t : todoList) {
                System.out.print(index +": ");
                todoTitles.add(t.getTitle());
                System.out.println(t.getTitle());
                index++;
            }

            System.out.println();
            checkItemCount(todoTitles);
            outputChoice();

            //사용자는 choice(create,edit,finish,delete,exit) 를 선택
            int choice = scan.nextInt();
            if (choice == 1) {
                Todo newTodo = new Todo().create();
                todoList.add(newTodo);
                bufferedWriter(todoList);
                sort(todoList);
            } else if (choice == 2) {
                todoList.add(new Todo().edit(todoList));
                bufferedWriter(todoList);
                sort(todoList);
            } else if (choice == 3) {
                new Todo().finish(todoTitles);
            } else if (choice == 4) {
                System.out.print("Delete TODO number: ");
                int deleteNum = scan.nextInt();
                todoList.remove(deleteNum);
                bufferedWriter(todoList);
                sort(todoList);
            } else if (choice == 5) {
                break;
            }
        }
    }

    public static void checkItemCount(List<String> titleList) {
        int count = 0;
        for (String title : titleList) {
            if (title.contains("done")) {
                continue;
            }
            count++;
        }
        System.out.printf("You have %d TODOs leftd", count);
    }

    public static void outputChoice() {
        System.out.println("1. Create TODO");
        System.out.println("2. Edit TODO");
        System.out.println("3. Finish TODO");
        System.out.println("4. Delete TODO");
        System.out.println("5. Exit");
        System.out.print("\n Input: ");
    }

    public static List<Todo> bufferedReader(List<Todo> todoList) {
        try (FileReader fileReader = new FileReader("todoExport.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line ;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                todoList.add(new Todo(elements[0], elements[1]));
            }
        } catch (IOException e) {
            System.out.println("파일을 열지 못했습니다");
            System.out.println(e.getMessage());
        }
        return todoList;
    }

    public static void bufferedWriter(List<Todo> todoList) {
        try (FileWriter fileWriter = new FileWriter(("todoExport.txt"));
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (Todo todo : todoList) {
                String line = new StringBuilder().append(todo.getTitle()).append(",")
                        .append(todo.getDate()).toString();
                writer.write(line);
            }
        } catch (IOException e) {
            System.out.println("데이터 작성을 위한 파일을 열지 못한다...");
            System.out.println(e.getMessage());
        }
    }

    public static void sort(List<Todo> todoList) {
        Collections.sort(todoList,new sortbyname());
    }
}
class sortbyname implements Comparator<Todo> {
    @Override
    public int compare(Todo t1, Todo t2) {
        return t1.getDate().compareTo(t2.getDate());
    }
}




















