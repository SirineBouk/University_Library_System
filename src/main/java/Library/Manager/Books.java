package Library.Manager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Books {
    private final SimpleIntegerProperty CBook;
    private final SimpleStringProperty title;
    private final SimpleStringProperty auth;
    private final SimpleStringProperty cate;
    private final SimpleIntegerProperty anne;
    private final SimpleIntegerProperty quan;

    public Books(int CBook, String title, String auth, String cate, int anne,int quan) {
        this.CBook = new SimpleIntegerProperty(CBook);
        this.title = new SimpleStringProperty(title);
        this.auth = new SimpleStringProperty(auth);
        this.cate = new SimpleStringProperty(cate);
        this.anne = new SimpleIntegerProperty(anne);
        this.quan = new SimpleIntegerProperty(quan);
    }

    public int getCBook() {
        return CBook.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuth() {
        return auth.get();
    }

    public String getCate() {
        return cate.get();
    }

    public int getAnne() {return anne.get();}

    public int getQuan() {return quan.get();}
}