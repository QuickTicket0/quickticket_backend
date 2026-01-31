import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceRoundScheduler {

    private final PerformanceService performanceService;

    // 예: 매일 00:00에 다음 회차 생성
    @Scheduled(cron = "0 0 0 * * *")
    public void createNextRounds() {
        performanceService.createNextRounds();
    }

    // 예: 1분마다 상태 변경(오픈/마감/종료) 체크
    @Scheduled(fixedDelay = 60_000)
    public void updateRoundStatus() {
        performanceService.updateRoundStatusByTime();
    }
}
