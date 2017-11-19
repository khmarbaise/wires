package fr.cla.wires;

import fr.cla.support.oo.ddd.AbstractValueObject;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

//@formatter:off
/**
 * A discretized time unit.
 */
public final class Tick extends AbstractValueObject<Tick> {
    public static final Tick ZERO = new Tick(0L);

    private final long tick;

    private Tick(long tick) {
        super(Tick.class);
        if(tick < 0) throw new IllegalArgumentException("tick must be >= 0, was: " + tick);
        this.tick = tick;
    }

    @Override
    protected List<Object> equalityCriteria() {
        return singletonList(tick);
    }

    public Tick plus(Delay delay) {
        return new Tick(tick + delay.duration());
    }

    public static Tick number(long number) {
        return new Tick(number);
    }

}
//@formatter:on