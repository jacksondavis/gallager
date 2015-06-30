package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

//A Player class which takes in buffered images to be used as the avatar and laser sprites, as well as an initial position
class Player(avatar:BufferedImage, initPos:Vec2, bulletPic:BufferedImage) extends Sprite(avatar, initPos) with ShootsBullets {
  private val speed = 10.0 
  
  def moveLeft() {
    val direction = new Vec2(-5,0)
    move(direction)
  }
  
  def moveRight() {
    val direction = new Vec2(5,0)
    move(direction)
  }
  
  def moveUp() {
    val direction = new Vec2(0,-5)
    move(direction)
  }
  
  def moveDown() {
    val direction = new Vec2(0,5)
    move(direction)
  }
  
  def shoot():Bullet = {
    new Bullet(bulletPic, pos, new Vec2(0,-speed*2))
  }
}