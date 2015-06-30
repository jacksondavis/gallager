package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage

//A Bullet class, which defines the a timeStep method to move a bullet by a given amount of velocity
class Bullet(pic: BufferedImage, initPos: Vec2, var vel: Vec2) extends Sprite(pic, initPos) {
  def timeStep() {
    pos += vel
  }
}