package pojo;

public class Topic {

    private Long id;
    private String hobby;
    private String title;
    private String desc;

    public Topic() {
    }

    public Topic(Long id, String hobby, String title, String desc) {
        this.id = id;
        this.hobby = hobby;
        this.title = title;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", hobby='" + hobby + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
