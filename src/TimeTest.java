import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;


public class TimeTest
{
	public String getPath() // get JAR path
	{
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").toLowerCase().contains("windows"))
            path = path.substring(1, path.length());
        if (path.contains("jar"))
        {
            path = path.substring(0, path.lastIndexOf("."));
            return path.substring(0, path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }
	
	public static String getDirPath() // add sep (marked as /)
	{
		try
		{
			String jarPath = URLDecoder.decode(new TimeTest().getPath(), "UTF-8");
			if (jarPath.endsWith("/") || jarPath.endsWith("\\"))
				return jarPath;
			else
				return jarPath + "/";
		}
		catch (UnsupportedEncodingException unDecExpt)
		{
			System.out.println(unDecExpt);
			return null;
		}
	}
	
	public static HashMap<String, Long> test(int m, int n, int timeCnt, int threadCnt)
    {
    	/* Initial */
    	HashMap<String, Long> Key_Value = new HashMap<>();
    	Key_Value.put("Test", (long)timeCnt);
    	Key_Value.put("Thread", (long)threadCnt);
    	Key_Value.put("m", (long)m);
    	Key_Value.put("n", (long)n);
    	int timeToTest = timeCnt / threadCnt;
    	
        PARS pars;
        Timer timer = new Timer();
        timer.setFormat(0, Timer.FORMAT.MICRO_SECOND);
        
        /* Setup */
        timer.start(0);
        pars = Setup.setup(160, m, n);
        long runTime = timer.stop(0);
        Key_Value.put("Setup_Time", runTime);
        Key_Value.put("Setup_Space", (getObjectSize(pars.get_mpk()) + getObjectSize(pars.get_msk())) / threadCnt);
        
        /* KGen */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
        	Element ID_i = pars.get_G1().newRandomElement().duplicate().getImmutable();
            timer.start(0);
            pars = KGen.kGen(pars, ID_i);
            runTime += timer.stop(0);
        }
        runTime /= timeCnt;
        Key_Value.put("KGen_Time", runTime);
        Key_Value.put("KGen_Space_sk", (getObjectSize(pars.get_sk_ID_i()) + (n << 3)) / threadCnt);
        Key_Value.put("KGen_Space_ek", (getObjectSize(pars.get_ek_ID_i()) + (n << 3)) / threadCnt);
        Key_Value.put("KGen_Space_ETP", (getObjectSize(pars.get_ETP()) + (n << 3)) / threadCnt);
        
        /* Encryption */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
        	Element[] ETP = new Element[n];
        	for (int j = 0; j < n; ++j)
        		ETP[j] = pars.get_G1().newRandomElement().duplicate().getImmutable();
            timer.start(0);
            pars = Encryption.encryption(pars);
            runTime += timer.stop(0);
        }
        runTime /= timeCnt;
        Key_Value.put("Encryption_Time", runTime);
        Key_Value.put("Encryption_Space", getObjectSize(pars.get_C()) / threadCnt);
        
        /* TokenGen */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
        	Element[] QTP = new Element[n];
        	for (int j = 0; j < n; ++j)
        		QTP[j] = pars.get_G1().newRandomElement().duplicate().getImmutable();
        	Element bsk_ID_i = pars.get_G1().newRandomElement().duplicate().getImmutable();
        	Element bsk = pars.get_G1().newRandomElement().duplicate().getImmutable();
            timer.start(0);
            pars = TokenGen.tokenGen(pars, QTP, bsk_ID_i, bsk);
            runTime += timer.stop(0);
        }
        runTime /= timeCnt;
        Key_Value.put("TokenGen_Time", runTime);
        Key_Value.put("TokenGen_Space", (getObjectSize(pars.get_token_pi()) + getObjectSize(pars.get_token())) / threadCnt);
        
        /* Query */
        timer.setFormat(0, Timer.FORMAT.NANO_SECOND);
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            Query.query(pars);
            runTime += timer.stop(0);
        }
        runTime /= timeCnt;
        Key_Value.put("Query_Time", runTime);
        
        /* Trace */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            Trace.trace(pars);
            runTime += timer.stop(0);
        }
        runTime /= timeCnt;
        Key_Value.put("Trace_Time", runTime);
        
        /* return */
        return Key_Value;
    }
    
    
    public static String Java2Python(ArrayList<HashMap<String, Long>> results)
    {
    	StringBuffer sb = new StringBuffer("[");
    	for (HashMap<String, Long> Key_Value : results)
    	{
    		sb.append("{");
    		sb.append("\"Test\":" + Key_Value.get("Test") + ", \"Thread\":" + Key_Value.get("Thread") + ", ");
    		sb.append("\"m\":" + Key_Value.get("m") + ", \"n\":" + Key_Value.get("n") + ", ");
    		sb.append("\"Setup_Time\":" + Key_Value.get("Setup_Time") + ", \"Setup_Space\":" + Key_Value.get("Setup_Space") + ", ");
    	    sb.append("\"KGen_Time\":" + Key_Value.get("KGen_Time") + ", \"KGen_Space_sk\":" + Key_Value.get("KGen_Space_sk") + ", ");
    	    sb.append("\"KGen_Space_ek\":" + Key_Value.get("KGen_Space_ek") + ", \"KGen_Space_ETP\":" + Key_Value.get("KGen_Space_ETP") + ", ");
    	    sb.append("\"Encryption_Time\":" + Key_Value.get("Encryption_Time") + ", \"Encryption_Space\":" + Key_Value.get("Encryption_Space") + ", ");
    	    sb.append("\"TokenGen_Time\":" + Key_Value.get("TokenGen_Time") + ", \"TokenGen_Space\":" + Key_Value.get("TokenGen_Space") + ", ");
    	    sb.append("\"Query_Time\":" + Key_Value.get("Query_Time") + ", \"Trace_Time\":" + Key_Value.get("Trace_Time"));
    	    sb.append("}, ");
    	}
    	/* remove the last ", " and append a "]" */
    	sb = sb.deleteCharAt(sb.length() - 1);
    	sb = sb.deleteCharAt(sb.length() - 1);
    	sb.append("]");
    	return sb.toString();
    }
    
    public static boolean dump(String str, String filepath, boolean isAlert, String encoding)
    {
        File newFile;
        try
        {
        	newFile = new File(filepath);
        	if (!newFile.exists())
        		newFile.createNewFile();
        }
        catch (Throwable e)
        {
        	if (isAlert)
        		System.out.println("Error creating file(s): \n" + e);
        	return false;
        }
        try
        {
            FileOutputStream out = new FileOutputStream(newFile, false);
            out.write(str.toString().getBytes(encoding));
            out.close();
            return true;
        }
        catch (Throwable e)
        {
        	if (isAlert)
        		System.out.println("Error writing file(s): \n" + e);
        	return false;
        }
    }
    public static boolean dump(String str, String filepath, boolean isAlert) { return dump(str, filepath, isAlert, "UTF-8"); }
    public static boolean dump(String str, String filepath) { return dump(str, filepath, true, "UTF-8"); }
    public static boolean dump(String str) { return dump(str, getDirPath() + "result.txt", true, "UTF-8"); }
    
    
    public static void main(String[] args)
    {
    	/* dispose args */
    	HashMap<String, Integer> argMap = new HashMap<String, Integer>();
    	String key = "", filepath = "";
    	for (String arg : args)
    	{
    		if (arg.toLowerCase().contains("timecnt"))
    			key = "timeCnt";
    		else if (arg.toLowerCase().contains("threadcnt"))
    			key = "threadCnt";
    		else if (arg.toLowerCase().contains("timetosleep"))
    			key = "timeToSleep";
    		else if (arg.toUpperCase().contains("MLB"))
    			key = "MLB";
    		else if (arg.toUpperCase().contains("MUB"))
    			key = "MUB";
    		else if (arg.toUpperCase().contains("MST"))
    			key = "MST";
    		else if (arg.toUpperCase().contains("NLB"))
    			key = "NLB";
    		else if (arg.toUpperCase().contains("NUB"))
    			key = "NUB";
    		else if (arg.toUpperCase().contains("NST"))
    			key = "NST";
    		else if (arg.contains("/") || arg.contains("\\"))
    			filepath = arg;
    		else
   				argMap.put(key, Integer.parseInt(arg));
    	}
    	
    	/* initial what to figure out */
    	ArrayList<HashMap<String, Long>> results = new ArrayList<>();
    	final int EXIT_SUCCESS = 0, EXIT_FAILURE = 1, EOF = (-1);
    	final int timeCnt = (null == argMap.get("timeCnt") ? 32 : argMap.get("timeCnt"));
    	final int threadCnt = (null == argMap.get("threadCnt") ? 16 : argMap.get("threadCnt"));
    	final int timeToSleep = (null == argMap.get("timeToSleep") ? 60000 : argMap.get("timeToSleep"));
    	
    	final int MLB = (null == argMap.get("MLB") ? 50 : argMap.get("MLB"));
    	final int MUB = (null == argMap.get("MUB") ? 100 : argMap.get("MUB"));
    	final int MST = (null == argMap.get("MST") ? 10 : argMap.get("MST"));
    	final int NLB = (null == argMap.get("NLB") ? 700 : argMap.get("NLB"));
    	final int NUB = (null == argMap.get("NUB") ? 1200 : argMap.get("NUB"));
    	final int NST = (null == argMap.get("NST") ? 100 : argMap.get("NST"));
    	
    	if (timeCnt % threadCnt != 0 || MLB > MUB || NLB > NUB) // assert(timeCnt % threadCnt == 0 && MLB <= MUB && NLB <= NUB);
    	{
    		System.out.println("Please make sure (timeCnt % threadCnt == 0 && MLB <= MUB && NLB <= NUB). ");
    		System.exit(EOF);
    	}
    	
    	if (filepath.length() <= 0)
    		filepath = getDirPath() + "result_" + threadCnt + ".txt";
    	
    	/* output file's path */
    	System.out.printf("Input: {\"timeCnt\":%d, \"threadCnt\":%d, \"timeToSleep\":%d, \"M\":range(%d, %d, %d), \"N\":range(%d, %d, %d)}\n", timeCnt, threadCnt, timeToSleep, MLB, MUB + 1, MST, NLB, NUB + 1, NST);
    	System.out.println("Output: \"" + filepath.replace("\\", "/").replace("/", System.getProperty("os.name").toLowerCase().contains("windows") ? "\\" : "/") + "\"");
    	
    	/* main loop */
    	for (int m = MLB; m <= MUB; m += MST)
    		for (int n = NLB; n <= NUB; n += NST)
	    	{
	    		final int tmp_m = m, tmp_n = n;
	    		for (int cnt = 0; cnt < threadCnt - 1; ++cnt)
	    		{
	    			new Thread(new Runnable()
	    			{
	    				public void run()
	    				{
	    					test(tmp_m, tmp_n, timeCnt, threadCnt);
	    				}
	    			}).start();
	    		}
	    		results.add(test(m, n, timeCnt, threadCnt));
	    		String toDump = Java2Python(results);
	    		System.out.println(toDump);
	    		if (!dump(toDump, filepath))
	    			System.exit(EXIT_FAILURE);
	    		if (MUB != m && NUB != n)
		    		try
		    		{
		    			Thread.sleep(timeToSleep);
		    		}
		    		catch (InterruptedException ie) {}
	    	}
    	
    	System.exit(EXIT_SUCCESS);
    	return;
    }
}