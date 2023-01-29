/*

BSD 3-Clause License

Copyright (c) 2023, Jack Meng

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class OwO
{
    public final double stutter, emoticon, expression, punctuation, alter;

    public final String payload;

    public OwO(String p, double st_d, double emot_d, double exp_d, double punc_d, double alter_d)
    {
        this.payload = p;
        this.stutter = st_d;
        this.emoticon = emot_d;
        this.expression = exp_d;
        this.punctuation = punc_d;
        this.alter = alter_d;
    }

    public static String db_str_OwO(OwO[] e)
    {
        StringBuilder sb = new StringBuilder();
        for (OwO r : e)
            sb.append("==================================\n").append(r.toString())
                    .append("==================================\n");
        return sb.toString();
    }

    private static String $make_tc0(String name, double er, boolean e)
    {
        return name + ": " + String.format("%.3f", er) + " -> " + e;
    }

    @Override public String toString()
    {
        return payload + "\nDiagnostics:\n" + $make_tc0("Stutter", stutter, stutter >= OwO_Conf.STUTTER_CHANCE) + "\n"
                + $make_tc0("Emoticon", emoticon, emoticon >= OwO_Conf.EMOTICON_CHANCE) + "\n"
                + $make_tc0("Expression", expression, expression >= OwO_Conf.EXPRESS_CHANCE) + "\n"
                + $make_tc0("Punctuation", punctuation, punctuation >= OwO_Conf.PUNCTUATION_CHANCE) + "\n"
                + $make_tc0("Alter", alter, alter >= OwO_Conf.ALTER_CHANCE) + "\n";
    }

    private static final class OwO_Conf
    {

        static final double STUTTER_CHANCE = 0.2d;
        static final double EMOTICON_CHANCE = 0.05d;
        static final double EXPRESS_CHANCE = 0.1d;
        static final double PUNCTUATION_CHANCE = 0.02d;
        static final double PUNCTUATION_CHANCE_MULTIPLIER = 0.1d;
        static final int PUNCTUATION_MAXIM = 4;
        static final double ALTER_CHANCE = 1d;

        static final java.util.Random RNG = new java.util.Random(392832903L);

        static final String[] _EMOTICONS_ = {
                "UwU",
                "OwO",
                ">w<",
                "Ow<",
                ">wO",
                ">3<",
                "TwT",
                "-w-",
                ";w;",
                ":3",
                ":v",
        };

        static final String[] _EMOTICONS_UTF8_ = {
                "\u00B7\\\\\\\u00B7",
                "\u00D2w\u00D3"
        };

        static final String[] _EXPRESS_EXTRA_ = {
                "hugs you",
                "winks",
                "wink wink",
                "senpai!",
                "baka",
                "kawaii",
                "<3"
        };

        static final String[] _EXPRESS_ = {
                "nuzzles",
                "meow",
                "nya",
                "nyea",
                "blushes"
        };

        static final String[] _DYN_ = {
                "purr>r>-",
                "rawr>r>-",
        };

        static final String[][] _COMMONS_ = {
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

        static final String[] _PUNCTUATION_ = {
                "~",
                "!",
                "?",
        };

    }

    static boolean roll(double chance_percent)
    {
        return OwO_Conf.RNG.nextDouble() <= chance_percent;
    }

    static < T > T roll(Supplier< T > res, T _default, double chance_percent)
    {
        return roll(chance_percent) ? res.get() : _default;
    }

    static String stut(String str, int index, int times)
    {
        if (index >= str.length() - 1)
            return str;
        return copy(str.substring(index), "-", times);
    }

    static String copy(String seq, String sep, int n)
    {
        StringBuilder sb = new StringBuilder();
        while (n-- > 0)
            sb.append(seq).append(sep);
        return sb.length() == 0 ? "" : sb.toString().substring(0, Math.abs(sb.toString().length() - 1));
    }

    static String dynm()
    {
        String t = OwO_Conf._DYN_[OwO_Conf.RNG.nextInt(OwO_Conf._DYN_.length)];
        return t.split(">")[0] + (roll(0.5d) ? "" : copy(t.split(">")[1], t.split(">")[2], OwO_Conf.RNG.nextInt(3)));
    }

    static String expr()
    {
        String x = roll(() -> "(", "", 0.5d);
        return x + "*"
                + (roll(0.5d) ? OwO_Conf._EXPRESS_[OwO_Conf.RNG.nextInt(OwO_Conf._EXPRESS_.length)] : dynm())
                + "*" + (x.equals("") ? "" : ")");
    }

    static String emot()
    {
        return OwO_Conf._EMOTICONS_[OwO_Conf.RNG.nextInt(OwO_Conf._EMOTICONS_.length)];
    }

    public static String owoify(String context)
    {
        StringBuilder sb = new StringBuilder();

        for (String word : context.split(" "))
        {
            if (roll(OwO_Conf.ALTER_CHANCE))
            {
                for (String[] reg : OwO_Conf._COMMONS_)
                    word = word.replace(reg[0], reg[1]);
                if (roll(OwO.OwO_Conf.STUTTER_CHANCE))
                    word = stut(word, 1, OwO_Conf.RNG.nextInt(3));
                if (roll(OwO_Conf.EMOTICON_CHANCE))
                    word = roll(0.5d) ? word + " " + emot() : emot() + " " + word;
                if (roll(OwO_Conf.EXPRESS_CHANCE))
                    word = roll(0.5d) ? word + " " + expr() : expr() + " " + word;
                if (roll(OwO_Conf.PUNCTUATION_CHANCE))
                {
                    int j = 0;
                    String e = OwO_Conf._PUNCTUATION_[OwO_Conf.RNG.nextInt(OwO_Conf._PUNCTUATION_.length)];
                    while (roll(OwO_Conf.PUNCTUATION_CHANCE_MULTIPLIER) && j <= OwO_Conf.PUNCTUATION_MAXIM)
                    {
                        word += e;
                        j++;
                    }
                }
            }
            sb.append(word).append(" ");
        }

        return sb.toString();
    }

    public static OwO[] owoify_2(String context)
    {
        List< OwO > words = new ArrayList<>(context.split(" ").length);

        for (String word : context.split(" "))
        {
            double alter = -1d, punc = -1d, expr = -1d, emot = -1d, stut = -1d;
            alter = OwO_Conf.RNG.nextDouble();
            punc = OwO_Conf.RNG.nextDouble();
            expr = OwO_Conf.RNG.nextDouble();
            emot = OwO_Conf.RNG.nextDouble();
            stut = OwO_Conf.RNG.nextDouble();
            if (alter <= OwO_Conf.ALTER_CHANCE)
            {

                for (String[] reg : OwO_Conf._COMMONS_)
                    word = word.replace(reg[0], reg[1]);
                if (stut >= OwO_Conf.STUTTER_CHANCE)
                    word = stut(word, 1, OwO_Conf.RNG.nextInt(3));
                if (emot >= OwO_Conf.EMOTICON_CHANCE)
                    word = roll(0.5d) ? word + " " + emot() : emot() + " " + word;
                if (expr >= OwO_Conf.EXPRESS_CHANCE)
                    word = roll(0.5d) ? word + " " + expr() : expr() + " " + word;
                if (punc >= OwO_Conf.PUNCTUATION_CHANCE)
                {
                    int j = 0;
                    String e = OwO_Conf._PUNCTUATION_[OwO_Conf.RNG.nextInt(OwO_Conf._PUNCTUATION_.length)];
                    while (roll(OwO_Conf.PUNCTUATION_CHANCE_MULTIPLIER) && j <= OwO_Conf.PUNCTUATION_MAXIM)
                    {
                        word += e;
                        j++;
                    }
                }
            }
            words.add(new OwO(word, stut, emot, expr, punc, alter));
        }

        return words.toArray(new OwO[0]);
    }

    public static void main(String... args)
    {
        int i = 1;
        for (String r : new String[] { "Life is a journey", "Every cloud has a silver lining", "Take one day at a time",
                "Carpe diem", "Make hay while the sun shines", "Early bird catches the worm",
                "Where there's smoke there's fire", "A picture is worth a thousand words",
                "Actions speak louder than words", "Practice makes perfect", "Every journey starts with a single step",
                "When life gives you lemons, make lemonade", "Don't judge a book by its cover",
                "A friend in need is a friend indeed", "Good things come to those who wait", "I love you" })
        {
            System.out.println(
                    "============================\n" + i + "\n" + r + "\n" + owoify(r));
            i++;
        }
        System.out.println("============================");
    }
}
