
public class booklist {
    private String name;
    private String bookname;
    private int date;
    private String status;

    public booklist(String name, String bookname, int date, String status){
        this.name = name;
        this.bookname = bookname;
        this.date = date;
        this.status = status;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getBookname(){
        return bookname;
    }
    public void setBookname(String bookname){
        this.bookname = bookname;
    }
    public Integer getDate(){
        return date;
    }
    public void setDate(int date){
        this.date = date;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
