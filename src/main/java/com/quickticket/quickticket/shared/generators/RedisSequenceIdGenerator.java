package com.quickticket.quickticket.shared.generators;


import com.quickticket.quickticket.shared.utils.AppContextUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisSequenceIdGenerator implements IdentifierGenerator {
    private final String valueKey;
    private RedisAtomicLong sequenceValue;

    public RedisSequenceIdGenerator(RedisSequenceId config) {
        this.valueKey = config.key();
    }

    @Override
    public Long generate(SharedSessionContractImplementor session, Object object) {
        if (this.sequenceValue == null) {
            var factory = AppContextUtils.getBean(RedisConnectionFactory.class);
            this.sequenceValue = new RedisAtomicLong(this.valueKey, factory);
        }

        return this.sequenceValue.incrementAndGet();
    }
}
