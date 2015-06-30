package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage
import java.awt.Graphics2D

//A class defining the movement and intersection for the scala shot laser powerup
class PowerLaser(img: BufferedImage, var pos2: Vec2) extends Sprite(img, pos2) {
  def moveLeft() {
    val direction = new Vec2(-5, 0)
    move(direction)
  }

  def moveRight() {
    val direction = new Vec2(5, 0)
    move(direction)
  }

  def moveUp() {
    val direction = new Vec2(0, -5)
    move(direction)
  }

  def moveDown() {
    val direction = new Vec2(0, 5)
    move(direction)
  }

  override  def intersects(other:Sprite): Boolean = {
    val l1x = pos.x - img.getWidth
    val h1x = pos.x + img.getWidth
    val l1y = pos.y - img.getHeight
    val h1y = pos.y + img.getHeight
    val l2x = other.pos.x - other.img.getWidth / 2
    val h2x = other.pos.x + other.img.getWidth / 2
    val l2y = other.pos.y - other.img.getHeight / 2
    val h2y = other.pos.y + other.img.getHeight / 2
    var overlapX = (l2x < h1x && l1x < h2x)
    var overlapY = (l2y < h1y && l1y < h2y)
    overlapX && overlapY
  }

  override def display(g: Graphics2D) {
    g.drawImage(img, (pos.x - img.getWidth() / 2).toInt, pos.y.toInt - img.getHeight, null)
  }
}