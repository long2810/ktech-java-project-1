import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Todo {
    private String title;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Todo(String title, String date) {
        this.title = title;
        this.date = date;
    }
    public Todo() {

    }
    Todo create(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Title: ");
        String title = scan.nextLine();
        System.out.print("Until: ");
        String date = scan.nextLine();
        System.out.println("Saved!!");
        return new Todo(title,date);
    }
    void edit(List<Todo> todoList){
        //editNumber를 선택한 후 todoList에서 editNumber에 해당한 Todo element를 꺼낸다
        System.out.print("Edit TODO number: ");
        Scanner scan = new Scanner(System.in);
        int editNumber = scan.nextInt() - 1;
        Todo editTodo = todoList.get(editNumber);
        //새로운 제목을 받음
        Scanner scan1 = new Scanner(System.in);
        System.out.print("Title: ");
        String title = scan1.nextLine();
        //새론운 date을 받음
        Scanner scan2 = new Scanner(System.in);
        System.out.print("Until: ");
        String date = scan2.nextLine();
        System.out.println("Saved!!");

        if (title == null) {
            title = editTodo.getTitle();
        }
        if (date == null) {
            date = editTodo.getTitle();
        }
        todoList.remove(editNumber);
        todoList.add(new Todo(title,date));
    }
    void finish(List<Todo> todoList){
        System.out.print("Finish TODO number: ");
        Scanner scan = new Scanner(System.in);
        int finishNumber = scan.nextInt() - 1;
        String todoTitle = todoList.get(finishNumber).getTitle() + "(done)";
        String date = todoList.get(finishNumber).getDate();
        todoList.set(finishNumber,new Todo(todoTitle,date));
    }

}
