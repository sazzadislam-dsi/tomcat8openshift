import com.lynas.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    static class Account {
        private Long id;
        private String name;
        private Book book;

        public Account(Long id, String name, Book book) {
            this.id = id;
            this.name = name;
            this.book = book;
        }

        public String getName() {
            return name;
        }
    }
    public static void main(String[] args) {
        List<Account> data1 = new ArrayList<>();
        data1.add(new Account(1L,"name",null));
        List<String> collect = data1.stream().map(account -> account.getName()).collect(Collectors.toList());

        System.out.println(collect);
    }
}
