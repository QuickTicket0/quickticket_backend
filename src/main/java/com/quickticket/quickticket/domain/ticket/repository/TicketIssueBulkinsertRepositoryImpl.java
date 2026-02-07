@Repository
@RequiredArgsConstructor
public class TicketIssueBulkinsertRepositoryImpl implements TicketIssueBulkinsertRepository {
    private static int EVENT_BUFFER_SIZE = 100;
  
    private final List<TicketInsertEvent> eventBuffer = new List<>();
  
    public Ticket saveDomainForBulk(Ticket domain) {
        if (domain.id != null) {
            // insert할 필요가 없음
            // 일반 메서드 호출?
        }
      
        this.eventBuffer.push();
        // redis 삽입
        // 삽입시 변경되는 필드 적용
        this.flushBuffer();
    }

    private void flushBuffer() {
        if (this.eventBuffer.length() < EVENT_BUFFER_SIZE) {
            return;
        }

        // rabbitmq로 전송
    }
}
