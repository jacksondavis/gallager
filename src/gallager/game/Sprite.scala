package gallager.game

import gallager.util.Vec2

import java.awt.Graphics2D
import java.awt.image.BufferedImage

//An abstract Sprite class containing methods that all sprites can use
abstract class Sprite(val img: BufferedImage, var pos: Vec2) {
  def move(direction: Vec2) {
    pos += direction
  }

  def moveTo(location: Vec2) {
    pos = location
  }

  def intersects(other:Sprite): Boolean = {
    val l1x = pos.x - img.getWidth / 2
    val h1x = pos.x + img.getWidth / 2
    val l1y = pos.y - img.getHeight / 2
    val h1y = pos.y + img.getHeight / 2
    val l2x = other.pos.x - other.img.getWidth / 2
    val h2x = other.pos.x + other.img.getWidth / 2
    val l2y = other.pos.y - other.img.getHeight / 2
    val h2y = other.pos.y + other.img.getHeight / 2
    var overlapX = (l2x < h1x && l1x < h2x)
    var overlapY = (l2y < h1y && l1y < h2y)
    overlapX && overlapY
  }

  def display(g: Graphics2D) {
    g.drawImage(img, (pos.x - img.getWidth() / 2).toInt, (pos.y - img.getHeight() / 2).toInt, null)
  }
}