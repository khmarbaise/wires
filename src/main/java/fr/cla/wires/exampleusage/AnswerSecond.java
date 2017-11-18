package fr.cla.wires.exampleusage;

import fr.cla.wires.Box;
import fr.cla.wires.Delay;
import fr.cla.wires.Time;
import fr.cla.wires.Wire;

public class AnswerSecond extends Box {

    private AnswerSecond(Wire<Boolean> in1, Wire<Boolean> in2, Wire<Boolean> out, Time time) {
        this(in1, in2, out, time, DEFAULT_DELAY);
    }

    private AnswerSecond(Wire<Boolean> in1, Wire<Boolean> in2, Wire<Boolean> out, Time time, Delay delay) {
        super(delay, time);
        //Warning not to let "this" escape through the method ref,
        // for a stateful Box that needs thread-safety
        // (otherwise the Box will not be "properly constructed").
        this.<Boolean, Boolean>onSignalChanged(in1).set(out).toResultOf(this::answerSecond, in2);
        this.<Boolean, Boolean>onSignalChanged(in2).set(out).toResultOf(in1, this::answerSecond);
    }


    private boolean answerSecond(boolean b1, boolean b2) {
        return b2;
    }

    public static Builder in1(Wire<Boolean> in1) {
        return new Builder(in1);
    }

    public static class Builder {
        private Wire<Boolean> in1, in2, out;

        public Builder(Wire<Boolean> in) {
            this.in1 = in;
        }

        public Builder in2(Wire<Boolean> in2) {
            this.in2 = in2;
            return this;
        }

        public Builder out(Wire<Boolean> out) {
            this.out = out;
            return this;
        }

        public AnswerSecond time(Time time) {
            return new AnswerSecond(in1, in2, out, time);
        }
    }

}
