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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class OwO
{
    private OwO()
    {
    }

    private static final class OwO_Conf
    {

        static final double STUTTER_CHANCE = 0.2d;
        static final double EMOTICON_CHANCE = 0.05d;
        static final double EXPRESS_CHANCE = 0.1d;
        static final double PUNCTUATION_CHANCE = 0.22d;
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
                ":3",
                ":v",
                ""
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
                "nyea"
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

        static final Set< String > BUCKET = new HashSet<>(5);

        static final int MAX_BUCKET_SZ = _EMOTICONS_.length + _EMOTICONS_UTF8_.length + _EXPRESS_EXTRA_.length
                + _EXPRESS_.length
                + _DYN_.length + _COMMONS_.length;
    }

    static boolean roll(double chance_percent)
    {
        return Math.random() <= chance_percent;
    }

    static < T > T roll(Supplier< T > res, T _default, double chance_percent)
    {
        return roll(chance_percent) ? res.get() : _default;
    }

    static String stut(String str, int index, int times)
    {
        if (index >= str.length() - 1)
            return str;
        return copy(str.substring(index - 1), "-", times);
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
                    word = stut(word, roll(0.5d) ? 1 : 2, OwO_Conf.RNG.nextInt(3));
                if (roll(OwO_Conf.EMOTICON_CHANCE))
                    word = roll(0.5d) ? word + " " + emot() : emot() + " " + word;
                if (roll(OwO_Conf.EXPRESS_CHANCE))
                    word = roll(0.5d) ? word + " " + expr() : expr() + " " + word;
            }
            sb.append(word).append(" ");
        }

        return sb.toString();
    }

    public static void main(String... args)
    {
        for (String r : new String[] { "Life is a journey", "Every cloud has a silver lining", "Take one day at a time",
                "Carpe diem", "Make hay while the sun shines", "Early bird catches the worm",
                "Where there's smoke there's fire", "A picture is worth a thousand words",
                "Actions speak louder than words", "Practice makes perfect", "Every journey starts with a single step",
                "When life gives you lemons, make lemonade", "Don't judge a book by its cover",
                "A friend in need is a friend indeed", "Good things come to those who wait" })
        {
            System.out.println(
                    "============================\n" + r + "\n" + owoify(r));
        }
        System.out.println("============================");
    }
}
