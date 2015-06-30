package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage
import java.awt.Graphics2D

//A PowerUp class which contains a movement pattern in a sine wave
class PowerUp(img: BufferedImage, var pos2: Vec2) extends Sprite(img,pos2){
  def move() {
    pos += new Vec2(-0.5, -(10 * math.sin(100 * pos.x)))
  }

  override def display(g: Graphics2D) {
    this.move()
    g.drawImage(img, (pos2.x - img.getWidth() / 2).toInt, (pos2.y - img.getHeight() / 2).toInt, null)
  }
}