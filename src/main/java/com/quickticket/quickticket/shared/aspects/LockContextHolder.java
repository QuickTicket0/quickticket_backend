package com.quickticket.quickticket.shared.aspects;

import java.util.HashMap;
import java.util.Map;

public class LockContextHolder {
    private static final ThreadLocal<Map<String, LockContext>> CONTEXTS
            = ThreadLocal.withInitial(HashMap::new);

    public static boolean isUnlockable(String key) {
        var lockContext = CONTEXTS.get().get(key);

        return lockContext != null
                && lockContext.isUnlockable();
    }

    public static void setPropagation(String key) {
        var lockContext = CONTEXTS.get().computeIfAbsent(key, k -> new LockContext(key));
        lockContext.setPropagation();
    }

    public static boolean tryLock(String key) {
        var lockContext = CONTEXTS.get().computeIfAbsent(key, k -> new LockContext(key));
        return lockContext.tryLock();
    }

    public static void decrement(String key) {
        Map<String, LockContext> contexts = CONTEXTS.get();
        var lockContext = contexts.get(key);

        if (lockContext == null) {
            throw new AssertionError("setPropagation() 혹은 tryLock()이 사전에 호출되지 않은 상황에서 decrement()가 호출되어선 안됨.");
        }

        lockContext.decrement();

        if (lockContext.getScopeDepth() <= 0) {
            contexts.remove(key);
        }
    }
}
