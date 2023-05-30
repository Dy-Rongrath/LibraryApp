import java.sql.Date;

public class borrowlist {
    private String username;
    private int bookid;
    private String borrowstatus;
    private Date date;
    private Date returndate;

    public borrowlist(String username, int bookid, String borrowstatus, Date date, Date returndate){
        this.username = username;
        this.bookid = bookid;
        this.borrowstatus = borrowstatus;
        this.date= date;
        this.returndate = returndate;
    }
    public String getUsername(){
        return username;
    }
    public int getBookid(){
        return bookid;
    }
    public String getBorrowstatus(){
        return borrowstatus;
    }
    public Date getDate(){
        return date;
    }
    public Date getReturndate(){
        return returndate;
    }
}