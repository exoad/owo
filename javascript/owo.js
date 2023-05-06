// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

const crypto = require("crypto");

const OwO_Conf = {
  STUTTER_CHANCE: 0.2,
  EMOTICON_CHANCE: 0.01,
  EMOTICON_UTF8_CHANCE: Buffer.isEncoding("utf8") ? 0.01 : 0,
  EXPRESS_CHANCE: 0.1,
  PUNCTUATION_CHANCE: 0.02,
  PUNCTUATION_CHANCE_MULTIPLIER: 0.1,
  PUNCTUATION_MAXIM: 4,
  ALTER_CHANCE: 1,
  RNG: crypto.createHash("sha256").update("392832903").digest(),
  _EMOTICONS_: [
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
  ],
  _EMOTICONS_UTF8_: ["\u00B7\\\\\\\u00B7", "\u00D2w\u00D3"],
  _EXPRESS_EXTRA_: [
    "hugs you",
    "winks",
    "wink wink",
    '"senpai!"',
    '"baka!!"',
    "kawaii",
    "<3",
  ],
  _EXPRESS_: ["nuzzles", "meow", "nya", "nyea", "blushes"],
  _DYN_: ["purr>r>-", "rawr>r>-"],
  _COMMONS_: [
    ["r", "w"],
    ["R", "W"],
    ["l", "w"],
    ["L", "W"],
    ["/ove/g", "uv"],
    ["oo", "u"],
  ],
  _PUNCTUATION_: ["~", "!", "?"],
};

function roll(chancePercent) {
  return crypto.randomBytes(1).readUInt8(0) / 255 <= chancePercent;
}

function rollWithDefault(res, defaultValue, chancePercent) {
  return roll(chancePercent) ? res() : defaultValue;
}

function stut(word) {
  const length = word.length;
  if (length <= 1) {
    return word;
  }
  const result = [];
  const stutterCount = length === 2 ? 1 : Math.floor(Math.random() * 2) + 1;
  for (let i = 0; i < stutterCount; i++) {
    result.push(word.slice(0, 2));
    if (i !== stutterCount - 1) {
      result.push("-");
    }
  }
  result.push(word.slice(2));
  return result.join("");
}

function copy(seq, sep, n) {
  return Array(n).fill(seq).join(sep);
}

function dynm() {
  // implementation here
}

class OwO {
  constructor (payload, stD, emotD, expD, puncD, alterD) {
    this.payload = payload;
    this.stutter = stD;
    this.emoticon = emotD;
    this.expression = expD;
    this.punctuation = puncD;
    this.alter = alterD;
  }

  static dbStrOwO(e) {
    let sb = "";
    for (const r of e) {
      sb += "==================================\n";
      sb += r.toString();
      sb += "==================================\n";
    }
    return sb;
  }

  addStutter() {
    if (this.stutter) {
      this.payload = this.payload
        .replace(/[lr]/g, (match) => {
          return match + "w";
        })
        .replace(/[LR]/g, (match) => {
          return match + "W";
        });
    }
    return this.payload;
  }

  addEmoticon() {
    if (this.emoticon) {
      this.payload += " owo";
    }
    return this.payload;
  }

  addExpression() {
    if (this.expression) {
      this.payload = "UwU " + this.payload + " OwO";
    }
    return this.payload;
  }

  addPunctuation() {
    if (this.punctuation) {
      this.payload = this.payload.replace(/[.!]/g, (match) => {
        return match + " " + this.alter + " ";
      });
    }
    return this.payload;
  }

  owoify() {
    let result = this.payload;
    result = this.addStutter();
    result = this.addEmoticon();
    result = this.addExpression();
    result = this.addPunctuation();
    return result;
  }
}

module.exports = OwO;
