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
