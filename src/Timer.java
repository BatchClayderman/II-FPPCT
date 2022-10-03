public class Timer
{
    public enum FORMAT
    {
        SECOND, 		// 0
        MILLI_SECOND, 	// 1
        MICRO_SECOND, 	// 2
        NANO_SECOND		// 3
    }

    public static final int DEFAULT_MAX_NUM_TIMER = 10;
    public final int MAX_NUM_TIMER;

    private long[] timeRecorder;
    private boolean[] isTimerStart;
    private FORMAT[] outFormat;

    public Timer()
    {
        this.MAX_NUM_TIMER = DEFAULT_MAX_NUM_TIMER;
        this.timeRecorder = new long[MAX_NUM_TIMER];
        this.isTimerStart = new boolean[MAX_NUM_TIMER];
        this.outFormat = new FORMAT[MAX_NUM_TIMER];

        /* set default format as millisecond */
        for (int i=0; i<outFormat.length; ++i)
            outFormat[i] = FORMAT.MILLI_SECOND;
    }

    public Timer(int max_num_timer)
    {
        this.MAX_NUM_TIMER = max_num_timer;
        this.timeRecorder = new long[MAX_NUM_TIMER];
        this.isTimerStart = new boolean[MAX_NUM_TIMER];
    }

    public void setFormat(int num, FORMAT format)
    {
        assert(num >= 0 && num < MAX_NUM_TIMER); // make sure num less than MAX_NUM_TIMER
        this.outFormat[num] = format;
    }

    public void start(int num)
    {
        assert(!isTimerStart[num]); // make sure the timer now stops.
        assert(num >=0 && num < MAX_NUM_TIMER); // make sure num less than MAX_NUM_TIMER
        isTimerStart[num] = true;
        timeRecorder[num] = System.nanoTime();
    }

    public long stop(int num)
    {
        assert(isTimerStart[num]); // make sure the timer now starts
        assert(num >=0 && num < MAX_NUM_TIMER); // make sure num less than MAX_NUM_TIMER
        long result = System.nanoTime() - timeRecorder[num];
        isTimerStart[num] = false;

        switch(outFormat[num])
        {
            case SECOND:
                return result / 1000000000L;
            case MILLI_SECOND:
                return result / 1000000L;
            case MICRO_SECOND:
                return result / 1000L;
            case NANO_SECOND:
                return result;
            default:
                return result / 1000000L;
        }
    }
}