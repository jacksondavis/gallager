package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage
import scala.swing.Swing
import java.awt.Graphics2D

//A Background class which creates a parallax effect 
class Background(var avatar1: BufferedImage, var avatar2: BufferedImage, var avatar3: BufferedImage, speed1: Int, speed2: Int, speed3: Int) {
  var pos1 = 0
  var pos2 = 0  
  var pos3 = 0  
  def display(g: Graphics2D) {
    g.drawImage(avatar1, 0, pos1, null)
    g.drawImage(avatar2, 0, pos2, null)
    g.drawImage(avatar3, 0, pos3, null)
    g.drawImage(avatar1, 0, pos1 - 600, null)
    g.drawImage(avatar2, 0, pos2 - 600, null)
    g.drawImage(avatar3, 0, pos3 - 600, null)
    pos1 += speed1
    pos2 += speed2
    pos3 += speed3
    if(pos1 > 600) {
      pos1 = 0
    }
    if(pos2 > 600) {
      pos2 = 0
    }
    if(pos3 > 600) {
      pos3 = 0
    }
  }
}
