# Projectile Plugin

This plugin was made to demonstrate skills, it showcases altering how projectiles behave depending on the type of projectile.

## Project Spec
Create custom projectiles that when launched, will attempt to follow the nearest target. Different
projectiles will have different ranges on following, and will behave differently.
- Arrow: Attempts to follow any entity within 5 blocks, for 3 seconds.
- Egg: Attempts to follow any nearby hostile creature within 20 blocks until it locks on.
- Enderpearl: Grabs the player’s cursor location (and target) and attempts to slowly
  approach that entity until it reaches it.
  Bonus: Modify the arrow project to ‘give up’ if the angle of change is too high. For example, if
  the arrow has to rotate more than 45 degrees in 1 second, it just gives up and ‘dies’.

## Features
- Arrow entities will attempt to follow any entity within 5 blocks for a maximum of 3 seconds
  - Bonus: If the angle to change is more than 45deg in a second it will "give up". I added a fun little angry effect aswell as making it plop on the ground
- Egg entities will attempt to follow any hostile mob in a 20 block radius
- Enderpearls will take the entity at your cursor location when thrown and slowly approach that entity until it locks on and hits it. Only happens given there is an entity at the cursor.

## Notes
- Raytracing was used to get the entity on the cursor. This has a time complexity of around O(n) so for testing purposes this is okay as n is the number of mobs around which should be small.
- The following code attempts to slowly change the velocity so it points in the direction of an entity. Uses funny vector math to keep the arrow going right.

This plugin is minimal and uses the concept of goals to achieve its functionality. This way you can dynamically assign goals based on what you want projectiles to do.
A way to scale this could be implementing general abstract goals like one for following, and then further ones based on mob types and range etc. For the purpose of this demonstration
I have only made one for each of the three types.