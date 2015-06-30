package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

//An Enemy class , taking in two buffered images to be used as avatar and laser sprites, as well as an initial position
class Enemy(pic: BufferedImage, initPos: Vec2, bulletPic: BufferedImage) extends Sprite(pic, initPos) with ShootsBullets {
  def shoot(): Bullet = {
    val bullet_image = bulletPic
    val vel = new Vec2(0, 10)
    val bull = new Bullet(bullet_image, pos, vel)
    bull
  }
}