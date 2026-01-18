@Builder
@Getter
public class Event {
    private Long id;
    private String name;
    private String description;
    private Category category1;
    private Category category2;
    private AgeRating ageRating;
    private Blob thumbnailImage;
    private String agentName;
    private String hostName;
    private String contactData;
    private Location location;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }
}