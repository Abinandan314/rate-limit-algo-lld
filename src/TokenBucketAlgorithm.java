import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TokenBucketAlgorithm {
    private Long tokens;
    private Instant lastRecordedTime;
    private final Integer rate;
    private final Long capacity;

    public TokenBucketAlgorithm(Long tokens, Instant lastRecordedTime, Integer rate, Long capacity) {
        this.tokens = tokens;
        this.lastRecordedTime = Instant.now();
        this.rate = rate;
        this.capacity = capacity;
    }

    private synchronized void refillBucket(){
        Instant currentTime = Instant.now();
        long timeInterval = ChronoUnit.MINUTES.between(this.lastRecordedTime,currentTime);
        if (timeInterval <= 0) return;
        tokens += (timeInterval * rate);
        tokens = Math.min(tokens,capacity);
        lastRecordedTime = currentTime;
    }

    public synchronized boolean allowRequest(int tokenCount){
        refillBucket();
        if (tokens - tokenCount < 0) return false;

        tokens-=tokenCount;
        return true;
    }
}
