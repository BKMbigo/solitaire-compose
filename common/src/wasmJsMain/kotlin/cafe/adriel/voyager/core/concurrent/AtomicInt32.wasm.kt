package cafe.adriel.voyager.core.concurrent

import kotlinx.atomicfu.atomic

public class AtomicInt32 constructor(initialValue: Int) {
    private val delegate = atomic(initialValue)
    public fun getAndIncrement(): Int {
        return delegate.incrementAndGet()
    }
}
