public class Bit {
    public enum boolValues {FALSE, TRUE}

    private boolValues val;

    public Bit(){
        this.val = boolValues.FALSE;
    }

    public Bit(boolean value) {
        if (value) {
            this.val = boolValues.TRUE;
        } else {
            this.val = boolValues.FALSE;
        }
    }

    public boolValues getValue() {
        return val;
    }

    public void assign(boolValues value) {
        val = value;
    }

    public void and(Bit b2, Bit result) {
        and(Bit.this, b2, result);
    }

    public static void and(Bit b1, Bit b2, Bit result) {
        if (b1.val.equals(boolValues.TRUE)) {
            if (b2.val.equals(boolValues.TRUE))
                result.assign(boolValues.TRUE);
        } else {
            result.assign(boolValues.FALSE);
        }
    }

    public void or(Bit b2, Bit result) {
        or(Bit.this, b2, result);
    }

    public static void or(Bit b1, Bit b2, Bit result) {
        if (b1.val.equals(boolValues.TRUE)) {
            result.assign(boolValues.TRUE);
        } else if (b2.val.equals(boolValues.TRUE)) {
            result.assign(boolValues.TRUE);
        } else {
            result.assign(boolValues.FALSE);
        }
    }

    public void xor(Bit b2, Bit result) {
        xor(Bit.this, b2, result);
    }

    public static void xor(Bit b1, Bit b2, Bit result) {
        switch (b1.val) {
            case TRUE:
                if(b2.val.equals(boolValues.FALSE))
                    result.assign(boolValues.TRUE);
                else
                    result.assign(boolValues.FALSE);
                break;
            case FALSE:
                if(b2.val.equals(boolValues.TRUE))
                    result.assign(boolValues.TRUE);
                else
                    result.assign(boolValues.FALSE);
                break;
        }
    }

    public static void not(Bit b2, Bit result) {
        if(b2.val.equals(boolValues.FALSE))
            result.assign(boolValues.TRUE);
        else
            result.assign(boolValues.FALSE);
    }

    public void not(Bit result) {
        not(Bit.this, result);
    }

    public String toString() {
        if (Bit.this.val.equals(boolValues.TRUE))
            return "t";
        return "f";
    }
}
