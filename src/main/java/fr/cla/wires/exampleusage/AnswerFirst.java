package fr.cla.wires.exampleusage;

import fr.cla.wires.Box;
import fr.cla.wires.Delay;
import fr.cla.wires.Time;
import fr.cla.wires.Wire;

import static java.util.Objects.requireNonNull;

public class AnswerFirst extends Box {

    private final Wire<Boolean> in1, in2, out;

    private AnswerFirst(Wire<Boolean> in1, Wire<Boolean> in2, Wire<Boolean> out, Time time) {
        this(in1, in2, out, time, DEFAULT_DELAY);
    }

    private AnswerFirst(Wire<Boolean> in1, Wire<Boolean> in2, Wire<Boolean> out, Time time, Delay delay) {
        super(delay, time);
        this.in1 = requireNonNull(in1);
        this.in2 = requireNonNull(in2);
        this.out = requireNonNull(out);
    }

    //Don't do the startup in the constructor to not let "this" escape through the method ref,
    // so that the Box is "properly constructed".
    private AnswerFirst startup() {
        this.<Boolean, Boolean>onSignalChanged(in1).set(out).toResultOf(this::answerFirst, in2);
        this.<Boolean, Boolean>onSignalChanged(in2).set(out).toResultOf(in1, this::answerFirst);
        return this;
    }

    private boolean answerFirst(boolean b1, boolean b2) {
        return b1;
    }

    public static Builder in1(Wire<Boolean> in1) {
        return new Builder(requireNonNull(in1));
    }

    public static class Builder {
        private Wire<Boolean> in1, in2, out;

        private Builder(Wire<Boolean> in) {
            this.in1 = requireNonNull(in);
        }

        public Builder in2(Wire<Boolean> in2) {
            this.in2 = requireNonNull(in2);
            return this;
        }

        public Builder out(Wire<Boolean> out) {
            this.out = requireNonNull(out);
            return this;
        }

        public AnswerFirst time(Time time) {
            Time _time = requireNonNull(time);
            return new AnswerFirst(in1, in2, out, _time).startup();
        }
    }

}
