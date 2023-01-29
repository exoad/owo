public class MyClass {
    static final java.util.Random RNG = new java.util.Random(69_420L);
    
    static final double STUTTER_CHANCE = 0.2d;
    static final double EMOTICON_CHANCE = 0.2d;
    static final double EXPRESS_CHANCE = 0.1d;
    static final double ALTER_CHANCE = 1d;
    static final int AVG_STUTTER = 3;
    
    static final String[] _EMOTICONS_ = 
    {
        "UwU",
        "OwO",
        ">w<",
        "TwT",
        ":3"
    };
    
    static final String[] _EXPRESS_ =
    {
        "Nuzzles you",
        "Meow"
    };
    
    static final String[] _DYN_ =
    {
        "Purr>r>-",
        "Rawr>r>-"
    };
    
    static final String[][] _COMMONS_ =
    {
        {
            "r",
            "w"
        },
        {
            "R",
            "W"
        },
        {
            "l",
            "w"
        },
        {
            "L",
            "W"
        },
        {
            "ove",
            "uv"
        },
    };
    
    static final boolean roll(double chance_percent)
    {
        return Math.random() <= chance_percent;
    }
    
    static final String stut(String str, int index, int max, int min)
    {
        if(index >= str.length() - 1) return str;
        return copy(str.substring(index), "-", RNG.nextInt(max - min) + min);
    }
    
    static final String copy(String seq, String sep, int n)
    {
        StringBuilder sb = new StringBuilder();
        while(n-- > 0) sb.append(seq).append(sep);
        return sb.toString().substring(0, Math.abs(sb.toString().length() - 1));
    }
    
    static final String dynm()
    {
        String t = _DYN_[RNG.nextInt(_DYN_.length)];
        return t.split(">")[0] + (roll(0.5d) ? "" : copy(t.split(">")[1], t.split(">")[2], RNG.nextInt(3)));
    }
    
    static final String expr()
    {
        return "(*" + (roll(0.5d) ? _EXPRESS_[RNG.nextInt(_EXPRESS_.length)] : dynm()) + "*)"; 
    }
    
    static final String emot()
    {
        return _EMOTICONS_[RNG.nextInt(_EMOTICONS_.length)];
    }
    
    public static String owoify(String context)
    {
        StringBuilder sb = new StringBuilder();
        
        for(String word : context.split(" "))
        {
            if(roll(ALTER_CHANCE))
            {
                for(String[] reg : _COMMONS_)
                    word = word.replace(reg[0], reg[1]);
                if(roll(STUTTER_CHANCE))
                    word = stut(word, roll(0.5d) ? 1 : 2, roll(0.5d) ? RNG.nextInt(AVG_STUTTER) : 2,  1);
                if(roll(EMOTICON_CHANCE))
                    word = roll(0.5d) ? word + " " + emot() : emot() + " " + word;
                if(roll(EXPRESS_CHANCE))
                    word = roll(0.5d) ? word + " " + expr() : expr() + " " + word;
            }
            sb.append(word).append(" ");
        }
        
        return sb.toString();
    }
    
    public static void main(String args[]) {
        System.out.println(owoify("Hello World"));
    }
}
