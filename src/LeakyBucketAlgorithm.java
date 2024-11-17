import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class LeakyBucketAlgorithm {
    private Long tokens;
    private Instant lastRecordedTime;
    private final Long capacity;
    private final Integer rate;
    private Long bucketSize;


    public LeakyBucketAlgorithm(Long tokens, Instant lastRecordedTime, Integer rate, Long capacity) {
        this.tokens = tokens;
        this.lastRecordedTime = Instant.now();
        this.rate = rate;
        this.capacity = capacity;
        this.bucketSize = 0L;
    }

    public synchronized boolean addData(int tokenCount){
        Instant currentTime = Instant.now();

        long timeElapsed = ChronoUnit.SECONDS.between(lastRecordedTime,currentTime);

        lastRecordedTime = currentTime;

        bucketSize = Math.max(0, bucketSize - timeElapsed * rate);

        if (bucketSize + tokenCount > capacity) return false;

        bucketSize+=tokenCount;

        return true;
    }
}
