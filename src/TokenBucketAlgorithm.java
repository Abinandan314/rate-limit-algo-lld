import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TokenBucketAlgorithm {
    private Long tokens;
    private Instant lastRecordedTime;
    private Integer rate;
    private Long capacity;

    public TokenBucketAlgorithm(Long tokens, Instant lastRecordedTime, Integer rate, Long capacity) {
        this.tokens = tokens;
        this.lastRecordedTime = Instant.now();
        this.rate = rate;
        this.capacity = capacity;
    }

    private void refillBucket(){
        Instant currentTime = Instant.now();
        long timeInterval = ChronoUnit.MINUTES.between(currentTime,this.lastRecordedTime);
        if (timeInterval <= 0) return;
        tokens += (timeInterval * rate);
        tokens = Math.min(tokens,capacity);
    }

    public boolean allowRequest(int tokenCount){
        refillBucket();
        if (tokens - tokenCount < 0) return false;

        tokens-=tokenCount;
        return true;
    }
}
