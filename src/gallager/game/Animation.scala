package gallager.game

import gallager.util.Vec2
import java.awt.Graphics2D
import java.awt.image.BufferedImage

//An Animation class which cycles through an array of bufferedimages
class Animation(var img: Array[BufferedImage], var index: Int, var pos: Vec2) {
  def display(g: Graphics2D) {
    g.drawImage(img(index), (pos.x - img(index).getWidth() / 2).toInt, (pos.y - img(index).getHeight() / 2).toInt, null)
    if (index < img.length - 1) {
      index += 1
    }
  }
}