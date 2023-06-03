
public class onholdlist {
    private int bookid;
    private String username;


    public onholdlist(int bookid, String username){
        this.bookid = bookid;
        this.username = username;

    }
    public int getBookid(){
        return bookid;
    }
    public String getUsername(){
        return username;
    }

}
