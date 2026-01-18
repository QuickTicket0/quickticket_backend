@Builder(access = PRIVATE)
@Getter
public class Category {
    private Long id;
    private String name;

    public void changeName(String newName) {
        validateName(newName);

        this.name = newName;
    }
    
    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }

    public static User create(String Name) {
        validateName(name);

        return User.builder()
            .name(name)
            .build();
    }

    public static User recreate(Long id, String Name) {
        return User.builder()
            .id(id)
            .name(name)
            .build();
    }

    private static void validateName(String name) {
        if (
            newName == null
            || newName.isBlank()
            || newName.length() > 20
        ) {
            throw new IllegalArgumentException();
        }
    }
}