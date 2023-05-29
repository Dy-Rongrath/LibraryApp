
public class booklist {
    private int bookid;
    private String bookname;
    private String authorname;
    private String subject;
    private String status;

    public booklist(int bookid, String bookname, String authorname, String subject, String status){
        this.bookid = bookid;
        this.bookname = bookname;
        this.authorname = authorname;
        this.subject = subject;
        this.status = status;
    }
    public int getBookid(){
        return bookid;
    }
    public String getBookname(){
        return bookname;
    }
    public String getAuthorname(){
        return authorname;
    }
    public String getSubject(){
        return subject;
    }
    public String getStatus(){
        return status;
    }
}

// public static void main(String[] args) {
    
// }
