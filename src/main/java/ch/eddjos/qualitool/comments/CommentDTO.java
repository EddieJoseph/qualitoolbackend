package ch.eddjos.qualitool.comments;

public class CommentDTO {
    public int commentId;
    public int authorId;
    public String authorName;
    public int personId;
    public String text;
    public int blockId;
    public String blockName;
    public int checkboxId;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", personId=" + personId +
                ", text='" + text + '\'' +
                ", blockId=" + blockId +
                ", blockName='" + blockName + '\'' +
                ", checkboxId=" + checkboxId +
                '}';
    }
}
