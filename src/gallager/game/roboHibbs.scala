package gallager.game

import gallager.util.Vec2
import java.awt.image.BufferedImage

/* A class used for the boss character. Contains movement methods used for mirroring the player. 
The variable hibbsAdvanced is referenced in roboHibb's shoot method to toggle a stronger attack*/
class RoboHibbs(avatar: BufferedImage, initPos: Vec2, bulletPic: BufferedImage) extends Enemy(avatar, initPos, bulletPic) {
  var hibbsAdvanced = false

  def mirrorLeft() {
    val direction = new Vec2(-5, 0)
    if (!(pos.x <= 40)) {
      move(direction)
    }
  }

  def mirrorRight() {
    val direction = new Vec2(5, 0)
    if (!(pos.x >= 760)) {
      move(direction)
    }
  }

  def mirrorUp() {
    val direction = new Vec2(0, 5)
    if (!(pos.y >= 600 - 64)) {
      move(direction)
    }
  }

  def mirrorDown() {
    val direction = new Vec2(0, -5)
    if (!(pos.y <= 40)) {
      move(direction)
    }
  }

  override def shoot(): Bullet = {
    val bullet_image = bulletPic
    val vel = new Vec2(0, 10)
    var bull: Bullet = null
    if (this.hibbsAdvanced) {
      bull = new Bullet(bullet_image, new Vec2(this.pos.x, (this.pos.y - 100)), vel)
    }
    if (!this.hibbsAdvanced) {
      bull = new Bullet(bullet_image, pos, vel)
    }
    bull
  }
}