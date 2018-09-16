import java.awt.Color;

interface IPaint {
  Color getFinalColor();
  int countPaints();
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
}

interface IMixture {
  Color getFinalColor();
  int countPaints();
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
}

class ExamplesPaint {
  IPaint red = new Solid("red", new Color(255, 0, 0));
  IPaint green = new Solid("green", new Color(0, 255, 0));
  IPaint blue = new Solid("blue", new Color(0, 0, 255));
  IPaint purple = new Combo("purple", new Blend(red, blue));
  IPaint darkPuriple = new Combo("dark purple", new Darken(purple));
  IPaint khaki = new Combo("khaki", new Blend(red, green));
  IPaint yellow = new Combo("yellow", new Brighten(khaki));
  IPaint mauve = new Combo("mauve", new Blend(purple, khaki));
  IPaint pink = new Combo("pink", new Brighten(mauve));
  IPaint coral = new Combo("coral", new Blend(pink, khaki));
  IPaint darkCoral = new Combo("darkCoral", new Darken(coral));
  IPaint lightBlue = new Combo("lightBlue", new Blend(blue, green));
  IPaint lightCoral = new Combo("lightCoral", new Brighten(coral));
}
