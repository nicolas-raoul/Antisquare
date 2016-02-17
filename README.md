Do some of your users see white squares like `□` ?
Antisquare helps you avoid those squares by telling you which font is appropriate to display a given character (among the fonts you choose to embed).

Usage:

1. Put your fonts in the `fonts` directory
2. Run `generate-database.sh`
3. Copy `Antisquare.java` and `AntisquareData.java` to your app
4. In your app, call: `Antisquare.getSuitableFonts('ណ')` and receive as a result: `"KhmerOS.ttf"`

Extremely fast: 25000 calls to getSuitableFonts take 1 millisecond
