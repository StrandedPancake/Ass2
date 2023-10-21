package model;

import java.util.Comparator;

public class PostComparator implements Comparator<SocialMediaPost> {
    private boolean compareByLikes;

    public PostComparator(boolean compareByLikes) {
        this.compareByLikes = compareByLikes;
    }

    @Override
    public int compare(SocialMediaPost post1, SocialMediaPost post2) {
        if (compareByLikes) {
            return Integer.compare(post2.getLikes(), post1.getLikes()); // Compare in descending order of likes
        } else {
            return Integer.compare(post2.getShares(), post1.getShares()); // Compare in descending order of shares
        }
    }
}
