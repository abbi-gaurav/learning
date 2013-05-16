package threading.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeadlockChecker {
	private final static ThreadMXBean tmb =
		ManagementFactory.getThreadMXBean();
	private final Set<Long> threadIdsToIgnore =
		new HashSet<Long>();

	/**
	 * Returns set of ThreadInfos that are part of the deadlock;
	 * could be empty if there is no deadlock.
	 */
	public Collection<ThreadInfo> check() {
		Map<Long, ThreadInfo> map = new HashMap<Long, ThreadInfo>();
		findDeadlockInfo(map, tmb.findMonitorDeadlockedThreads());
		findDeadlockInfo(map, tmb.findDeadlockedThreads());
		return map.values();
	}

	public void ignoreThreads(Thread[] threads) {
		for (Thread thread : threads) {
			threadIdsToIgnore.add(thread.getId());
		}
	}

	private void findDeadlockInfo(Map<Long, ThreadInfo> result,
			long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				if (!threadIdsToIgnore.contains(id)) {
					result.put(id, tmb.getThreadInfo(id));
				}
			}
		}
	}
}
