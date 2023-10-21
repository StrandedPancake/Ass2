package model;

public class SocialMediaPost {
    private int id;
    private String content;
    private String author;
    private int likes;
    private int shares;

    public SocialMediaPost(int id, String content, String author, int likes, int shares) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

	public static void addPost(Post newPost) {
		// TODO Auto-generated method stub
		
	}
}
