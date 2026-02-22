package com.quickticket.quickticket.shared.aspects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class LockContext {
    @Getter private final String lockKey;
    @Getter private int scopeDepth = 0;
    @Getter private boolean isLocked = false;
    private int lockDepth = -1;
    private final List<Integer> encloseDepths = new ArrayList<>();

    public boolean isLockable() {
        return !this.isLocked;
    }

    public boolean isUnlockable() {
        return this.isLocked
                && !this.isPropagationActive()
                && this.scopeDepth <= this.lockDepth;
    }

    public boolean isPropagationActive() {
        return !this.encloseDepths.isEmpty();
    }

    public void setPropagation() {
        this.scopeDepth++;
        this.encloseDepths.add(this.scopeDepth);
    }

    public boolean tryLock() {
        this.scopeDepth++;

        if (this.isLockable()) {
            this.isLocked = true;
            this.lockDepth = this.scopeDepth;
            return true;
        } else {
            return false;
        }
    }

    public void decrement() {
        this.scopeDepth--;

        if (this.encloseDepths.getLast() > this.scopeDepth) {
            this.encloseDepths.removeLast();
        }

        if (this.isUnlockable()) {
            this.isLocked = false;
            this.lockDepth = -1;
        }
    }
}
