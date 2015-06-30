package gallager.game

import gallager.util.Vec2
import java.io.File
import scala.swing.Panel
import javax.imageio.ImageIO
import scala.swing.MainFrame
import java.awt.Graphics2D
import java.awt.Dimension
import java.awt.geom.Rectangle2D
import java.awt.Color

//A main object to instantiate a MainFrame containing SpaceGamePanel
object SpaceGameApp {
  val gamePanel = new SpaceGamePanel
  val frame = new MainFrame {
    title = "Gallager +"
    contents = gamePanel
    centerOnScreen
  }

  def main(args: Array[String]) {
    frame.open()
    gamePanel.startGame()
    gamePanel.requestFocus
  }
}