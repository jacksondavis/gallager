package gallager.util

class Vec2(var x:Double, var y:Double) {
  def +(rhs:Vec2):Vec2 = {
    new Vec2(this.x + rhs.x, this.y + rhs.y)
  }
  def -(rhs:Vec2):Vec2 = {
    new Vec2(this.x - rhs.x, this.y - rhs.y)
  }

  def *(rhs:Double):Vec2 = {
    new Vec2(this.x * rhs, this.y * rhs)
  }
  def /(rhs:Double):Vec2 = {
    new Vec2(this.x / rhs, this.y / rhs)
  }

  def magnitude():Double = {
    math.sqrt(x*x + y*y)
  }
  def length():Double = magnitude
  def normalize():Vec2 = {
    this / length
  }

  //Methods to calculate the dot product, and determine the angle between 2 vectors
  def dot(other:Vec2):Double = this.x*other.x + this.y*other.y
  def angleBetween(other:Vec2):Double = math.acos( dot(other) / (length * other.length) )

  //Additional methods for graphics use
  def distance(other:Vec2):Double = math.sqrt(sqDistance(other))
  def sqDistance(other:Vec2):Double = (x-other.x)*(x-other.x) + (y-other.y)*(y-other.y)

  override def toString():String = "(" + x + ", " + y + ")"
}

object Vec2Tester {
  def main(args:Array[String]) {
    
  }
}