import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

//Sort을 위해 subClass 생성
class Sortbydate implements Comparator<Todo> {
    @Override
    public int compare(Todo t1, Todo t2) {
        return t1.getDate().compareTo(t2.getDate());
    }
}

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome");
            Scanner scan = new Scanner(System.in);
            List<Todo> todoList = new ArrayList<>();
            //파일에서 있는 데이터를 다시 todoList에 작성한다
            todoList = bufferedReader(todoList);
            sort(todoList);
            //제목들을 갖는 todoTitles를 만들다
            List<String> todoTitles = new ArrayList<>();
            for (Todo t : todoList) {
                todoTitles.add(t.getTitle());
            }
            System.out.println();
            checkItemCount(todoTitles);
            int index = 1;
            for (Todo t : todoList) {
                System.out.print(index + ": ");
                System.out.println(t.getTitle());
                index++;
            }
            outputChoice();

            //사용자는 choice(create,edit,finish,delete,exit) 를 선택
            //Exception 처리
            int choice;
            try {
                choice = Integer.parseInt(scan.next());
            } catch (NumberFormatException a) {
                System.out.println("input is not number");
                break;
            }
            if (choice == 1) {
                //Todo클래스의 create 메소드를 사용함 -> 새로운 Todo클래스의 instance를 만들다
                Todo newTodo = new Todo().create();
                // 그 instance를 todoList에 추가함
                todoList.add(newTodo);
                // todoList을 정렬한다
                sort(todoList);
                //todoList의 elements를 파일에 작성한다
                bufferedWriter(todoList);
            } else if (choice == 2) {
                //Todo클래스의 edit 메소드를 사용함
                new Todo().edit(todoList);
                sort(todoList);
                bufferedWriter(todoList);
            } else if (choice == 3) {
                //Todo클래스의 finish 메소드를 사용함
                new Todo().finish(todoList);
                bufferedWriter(todoList);
            } else if (choice == 4) {
                System.out.print("Delete TODO number: ");
                int deleteNum = scan.nextInt() - 1;
                todoList.remove(deleteNum);
                sort(todoList);
                bufferedWriter(todoList);
            } else if (choice == 5) {
                break;
            }
        }
        System.out.println();
    }

    // (done)완료된 elelement의 수를 검사하는 메소드
    public static void checkItemCount(List<String> titleList) {
        int count = 0;
        for (String title : titleList) {
            if (title == null || title.contains("done")) {
                continue;
            }
            count++;
        }
        System.out.printf("You have %d TODOs left \n", count);
    }

    // 각 선택들을 출력한다
    public static void outputChoice() {
        System.out.println("\n1. Create TODO");
        System.out.println("2. Edit TODO");
        System.out.println("3. Finish TODO");
        System.out.println("4. Delete TODO");
        System.out.println("5. Exit");
        System.out.print("\n Input: ");
    }

    //파일에서 있는 데이터를 todoList에 작성함
    public static List<Todo> bufferedReader(List<Todo> todoList) {
        try (FileReader fileReader = new FileReader("todoExport.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
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

    // todoList에 있는 데이터를 모두 파일을 전달해주는 bufferedWriter
    public static void bufferedWriter(List<Todo> todoList) {
        try (FileWriter fileWriter = new FileWriter(("todoExport.txt"));
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (Todo todo : todoList) {
                String line = new StringBuilder().append(todo.getTitle()).append(",")
                        .append(todo.getDate()).toString();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("데이터 작성을 위한 파일을 열지 못한다...");
            System.out.println(e.getMessage());
        }
    }

    //SortbyDate SubClass을 바탕으로 sort method를 만들다
    public static void sort(List<Todo> todoList) {
        Collections.sort(todoList, new Sortbydate());
    }
}




















