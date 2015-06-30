package gallager.game

import gallager.util.Vec2
import java.awt.Graphics2D
import java.io.File
import javax.imageio.ImageIO
import scala.util.Random
import scala.collection.mutable.Buffer

//An EnemySwarm class which creates a 2D Buffer of enemies based on the specified number of rows and columns
class EnemySwarm(val nRows: Int, val nCols: Int) extends ShootsBullets {

  val enemyPic = ImageIO.read(new File("sprites/enemy/enemy.png"))
  val enemyBullet = ImageIO.read(new File("sprites/bullets/enemyBullet.png"))
  var enemies: Buffer[Enemy] = Buffer.tabulate(nRows * nCols)(i => {
    new Enemy(enemyPic, new Vec2(150 + (i % nCols) * 75, 25 + (i / nCols) * 50), enemyBullet)
  })

  def display(g: Graphics2D) {
    for (enemy <- enemies) enemy.display(g)
  }

  def shoot(): Bullet = {
    //Choose a random enemy to shoot
    if (!enemies.isEmpty) {
      enemies(Random.nextInt(enemies.length)).shoot()
    }
    else null
  }
}