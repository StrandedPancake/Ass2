package SocialMediaAnalyzer3;
public class Post {
    private int id;
    private String content;
    private String author;
    private int likes;
    private int shares;
    private String dateTime;

    public Post(int id, String content, String author, int likes, int shares, String dateTime) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getAuthor() {
        return author;
    }
    public int getLikes() {
        return likes;
    }
    public int getShares() {
        return shares;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setShares(int shares) {
        this.shares = shares;
    }
    
    public String toString() {
        return "ID: " + id +
                ", Content: " + content +
                ", Author: " + author +
                ", Likes: " + likes +
                ", Shares: " + shares +
                ", Date and Time: " + dateTime;
    }
}
