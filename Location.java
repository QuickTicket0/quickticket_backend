@Builder
@Getter
public class Location {
    private Long id;
    private String name;
    private String zipNumber;
    /// 시/도
    private String sido;
    /// 시/군/구
    private String siGunGu;
    /// 읍/면
    private String eupMyun;
    /// 도로번호
    private String doroCode;
    /// 도로명
    private String doroName;
    private String phone;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }
}