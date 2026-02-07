@Repository
@RequiredArgsConstructor
public class TicketIssueBulkinsertRepositoryImpl implements TicketIssueBulkinsertRepository {
    private static int EVENT_BUFFER_SIZE = 100;
    
    private final RedisAtomicLong ticketIssueIdGenerator;
    private final RedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final TicketIssueMapper ticketIssueMapper;
    private final @Lazy TicketIssueRepository ticketIssueRepository;
    private final List<TicketInsertEvent> eventBuffer = new List<>();

    public Ticket saveDomainForBulk(Ticket domain) {
        if (domain.getId() != null) {
            ticketIssueRepository.saveDomain(domain);
            return;
        }

        var entity = ticketIssueMapper.toEntity(domain);
        entity.id = ticketIssueIdGenerator.incrementAndGet();

        redisTemplate.opsForValue().set("temp:ticketIssue:" + entity.id, entity);
        
        this.addToBuffer(new TicketInsertEvent(entity));
        
        return ticketIssueMapper.toDomain(entity);
    }

    private void addToBuffer(TicketInsertEvent event) {
        this.eventBuffer.push(event);
        
        if (this.eventBuffer.length() < EVENT_BUFFER_SIZE) {
            return;
        }

        rabbitTemplate.convertAndSend("worker.bulkinsert.exchange", "worker.bulkinsert.ticket", eventBuffer);
    }
}
