import java.awt.Color;

import tester.Tester;

interface IPaint {
  Color getFinalColor();

  int countPaints();

  int countMixes();

  int formulaDepth();

  String mixingFormula(int depth);
}

class Solid implements IPaint {
  String name;
  Color color;

  Solid(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  @Override
  public Color getFinalColor() {
    return this.color;
  }

  @Override
  public int countPaints() {
    return 1;
  }

  @Override
  public int countMixes() {
    return 0;
  }

  @Override
  public int formulaDepth() {
    return 0;
  }

  @Override
  public String mixingFormula(int depth) {
    return this.name;
  }
}

class Combo implements IPaint {
  String name;
  IMixture operation;

  Combo(String name, IMixture operation) {
    this.name = name;
    this.operation = operation;
  }

  @Override
  public Color getFinalColor() {
    return this.operation.getFinalColor();
  }

  @Override
  public int countPaints() {
    return operation.countPaints();
  }

  @Override
  public int countMixes() {
    return operation.countMixes();
  }

  @Override
  public int formulaDepth() {
    return operation.formulaDepth();
  }

  @Override
  public String mixingFormula(int depth) {
    if (depth <= 0) {
      return this.name;
    }
    return this.operation.mixingFormula(depth - 1);
  }
}

interface IMixture {
  Color getFinalColor();

  int countPaints();

  int countMixes();

  int formulaDepth();

  String mixingFormula(int depth);
}

class Darken implements IMixture {
  IPaint paint;

  Darken(IPaint paint) {
    this.paint = paint;
  }

  @Override
  public Color getFinalColor() {
    return this.paint.getFinalColor().darker();
  }

  @Override
  public int countPaints() {
    return paint.countPaints() + 1;
  }

  @Override
  public int countMixes() {
    return paint.countMixes() + 1;
  }

  @Override
  public int formulaDepth() {
    return paint.formulaDepth() + 1;
  }

  @Override
  public String mixingFormula(int depth) {
    return "darken(" + paint.mixingFormula(depth) + ")";
  }

}

class Brighten implements IMixture {
  IPaint paint;

  Brighten(IPaint paint) {
    this.paint = paint;
  }

  @Override
  public Color getFinalColor() {
    return this.paint.getFinalColor().brighter();
  }

  @Override
  public int countPaints() {
    return paint.countPaints() + 1;
  }

  @Override
  public int countMixes() {
    return paint.countMixes() + 1;
  }

  @Override
  public int formulaDepth() {
    return paint.formulaDepth() + 1;
  }

  @Override
  public String mixingFormula(int depth) {
    return "brighten(" + paint.mixingFormula(depth) + ")";
  }
}

class Blend implements IMixture {
  IPaint paint1;
  IPaint paint2;

  Blend(IPaint paint1, IPaint paint2) {
    this.paint1 = paint1;
    this.paint2 = paint2;
  }

  @Override
  public Color getFinalColor() {
    Color paint1Color = this.paint1.getFinalColor();
    Color paint2Color = this.paint2.getFinalColor();
    return new Color(paint1Color.getRed() / 2 + paint2Color.getRed() / 2,
        paint1Color.getGreen() / 2 + paint2Color.getGreen() / 2,
        paint1Color.getBlue() / 2 + paint2Color.getBlue() / 2);
  }

  @Override
  public int countPaints() {
    return paint1.countPaints() + paint2.countPaints();
  }

  @Override
  public int countMixes() {
    return paint1.countMixes() + paint2.countMixes() + 1;
  }

  @Override
  public int formulaDepth() {
    return Math.max(paint1.formulaDepth(), paint2.formulaDepth()) + 1;
  }

  @Override
  public String mixingFormula(int depth) {
    return "blend(" + paint1.mixingFormula(depth) + ", " + paint2.mixingFormula(depth) + ")";
  }
}

class ExamplesPaint {
  IPaint red = new Solid("red", new Color(255, 0, 0));
  IPaint green = new Solid("green", new Color(0, 255, 0));
  IPaint blue = new Solid("blue", new Color(0, 0, 255));
  IPaint purple = new Combo("purple", new Blend(red, blue));
  IPaint darkPurple = new Combo("dark purple", new Darken(purple));
  IPaint khaki = new Combo("khaki", new Blend(red, green));
  IPaint yellow = new Combo("yellow", new Brighten(khaki));
  IPaint mauve = new Combo("mauve", new Blend(purple, khaki));
  IPaint pink = new Combo("pink", new Brighten(mauve));
  IPaint coral = new Combo("coral", new Blend(pink, khaki));
  IPaint darkCoral = new Combo("darkCoral", new Darken(coral));
  IPaint lightBlue = new Combo("lightBlue", new Blend(blue, green));
  IPaint lightCoral = new Combo("lightCoral", new Brighten(coral));

  IPaint doubleDarkPurple = new Combo("double dark purple", new Blend(darkPurple, darkPurple));

  boolean testGetFinalColor(Tester t) {
    // TODO: implement
    return false;
  }

  boolean testCountPaints(Tester t) {
    return t.checkExpect(darkPurple.countPaints(), 3);
  }

  boolean testCountMixes(Tester t) {
    return t.checkExpect(darkPurple.countMixes(), 2)
        && t.checkExpect(doubleDarkPurple.countMixes(), 5);
  }

  boolean testFormulaDepth(Tester t) {
    return t.checkExpect(darkPurple.formulaDepth(), 2)
        && t.checkExpect(doubleDarkPurple.formulaDepth(), 3);
  }

  IPaint testPink = new Combo("pink",
      new Brighten(new Combo("darkpink", new Blend(new Combo("purple", new Blend(red, blue)),
          new Combo("khaki", new Blend(red, green))))));

  boolean testMixingFormula1(Tester t) {
    return t.checkExpect(testPink.mixingFormula(0), "pink");
  }

  boolean testMixingFormula2(Tester t) {
    return t.checkExpect(testPink.mixingFormula(2), "brighten(blend(purple, khaki))");
  }

  boolean testMixingFormula3(Tester t) {
    return t.checkExpect(testPink.mixingFormula(3),
        "brighten(blend(blend(red, blue), blend(red, green)))");
  }
}
