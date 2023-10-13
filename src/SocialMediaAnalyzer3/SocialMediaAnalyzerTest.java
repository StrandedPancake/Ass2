package SocialMediaAnalyzer3;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaAnalyzerTest {

    @Test
    public void testGetters() {
        Post post = new Post(1, "Test Content", "Author", 15, 8, "2023-08-16 15:00");
        assertEquals(1, post.getId());
        assertEquals("Test Content", post.getContent());
        assertEquals("Author", post.getAuthor());
        assertEquals(15, post.getLikes());
        assertEquals(8, post.getShares());
        assertEquals("2023-08-16 15:00", post.getDateTime());
    }

    @Test
    public void testSetters() {
        Post post = new Post(1, "Test Content", "Author", 15, 8, "2023-08-16 15:00");
        post.setLikes(20);
        post.setShares(10);
        assertEquals(20, post.getLikes());
        assertEquals(10, post.getShares());
    }


    @Test
    public void testCompareByShares() {
        Post post1 = new Post(1, "Content 1", "Author 1", 10, 5, "2023-08-16 12:00");
        Post post2 = new Post(2, "Content 2", "Author 2", 15, 10, "2023-08-16 14:00");
        PostComparator comparator = new PostComparator(false);

        assertTrue(comparator.compare(post1, post2) > 0);
    }


    @Test
    public void testAddPost() {
        List<Post> initialPosts = new ArrayList<>();
        String testCsvFilePath = "test_posts.csv";
        SocialMediaAnalyzer analyzer = new SocialMediaAnalyzer(initialPosts, testCsvFilePath);

        Post newPost = new Post(3, "New Test Content", "New Author", 15, 8, "2023-08-16 15:00");
        analyzer.addPost(newPost);

        assertNotNull(analyzer.retrievePost(3));
    }

    @Test
    public void testRemovePost() {
        List<Post> initialPosts = new ArrayList<>();
        initialPosts.add(new Post(1, "Test Content 1", "Author 1", 10, 5, "2023-08-16 12:00"));
        initialPosts.add(new Post(2, "Test Content 2", "Author 2", 20, 10, "2023-08-16 14:00"));
        String testCsvFilePath = "test_posts.csv";
        SocialMediaAnalyzer analyzer = new SocialMediaAnalyzer(initialPosts, testCsvFilePath);

        assertTrue(analyzer.removePost(1));
        assertNull(analyzer.retrievePost(1));
    }
}
