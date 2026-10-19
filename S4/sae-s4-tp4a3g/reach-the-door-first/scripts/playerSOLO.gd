extends CharacterBody2D

# --- Paramètres de physique "Hardcore" ---
const SPEED = 300.0
const ACCELERATION = 3000.0
const FRICTION = 4000.0
const JUMP_VELOCITY = -550.0
const MIN_JUMP_VELOCITY = -200.0

# --- Paramètres du Wall Jump ---
const WALL_JUMP_PUSH = 500.0
const WALL_SLIDE_SPEED = 150.0

# --- Coyote Time ---
const COYOTE_TIME = 0.15
var coyote_timer = 0.0
var team_name: String = "rouge"
var gravity = ProjectSettings.get_setting("physics/2d/default_gravity")

@onready var spawn_point = getSpawnPoint()
@onready var spawn_position = spawn_point.global_position

@onready var ap = $AnimatedSprite2D

@onready var jump_sound = $JumpSound
@onready var death_sound = $DeathSound

@onready var spawn_sound = $SpawnSound

func _ready():
	global_position = spawn_position
	spawn_sound.play()

func _physics_process(delta):
	var input_dir = Input.get_axis("ui_left", "ui_right")
	var jump_just_pressed = Input.is_action_just_pressed("ui_up")
	var jump_released = Input.is_action_just_released("ui_up")

	# 1. Gravité et Wall Slide
	if not is_on_floor():
		coyote_timer -= delta
		if is_on_wall() and velocity.y > 0 and input_dir != 0:
			velocity.y = min(velocity.y + gravity * delta, WALL_SLIDE_SPEED)
		else:
			velocity.y += gravity * delta
	else:
		coyote_timer = COYOTE_TIME

	# 2. Gestion du Saut
	if jump_just_pressed and coyote_timer > 0.0:
		velocity.y = JUMP_VELOCITY
		coyote_timer = 0.0
		ParticleManager.spawn("jump", global_position)
		#jump_sound.pitch_scale = randf_range(1.4, 1.6)
		jump_sound.play(0.20)
		await get_tree().create_timer(0.4).timeout 
		jump_sound.stop()
	
	if jump_released and velocity.y < MIN_JUMP_VELOCITY:
		velocity.y = MIN_JUMP_VELOCITY

	# 3. Gestion du Wall Jump
	if jump_just_pressed and is_on_wall() and not is_on_floor():
		var wall_normal = get_wall_normal()
		velocity.y = JUMP_VELOCITY
		velocity.x = wall_normal.x * WALL_JUMP_PUSH
		ParticleManager.spawn("jump", global_position)
		jump_sound.play(0.20)
		await get_tree().create_timer(0.4).timeout 
		jump_sound.stop()

	# 4. Déplacement horizontal
	if input_dir != 0:
		velocity.x = move_toward(velocity.x, input_dir * SPEED, ACCELERATION * delta)
	else:
		velocity.x = move_toward(velocity.x, 0, FRICTION * delta)

	# 5. Mise à jour de l'animation
	update_animation(input_dir)

	move_and_slide()

func update_animation(direction):
	# On gère l'orientation (gauche/droite) indépendamment de l'animation
	if direction != 0:
		ap.flip_h = direction < 0

	# Logique de choix de l'animation
	if not is_on_floor():
		# Si le personnage n'est pas au sol, il joue l'animation de saut
		ap.play("jump")
		# ---  ON ARRÊTE LES PAS EN L'AIR ---
		
	else:
		# S'il est au sol, on regarde s'il bouge horizontalement
		if direction != 0:
			ap.play("walk")
			# ---  ON JOUE LES PAS AU SOL ---
			
		else:
			ap.play("idle")
			# ---  ON ARRÊTE LES PAS À L'ARRÊT ---
			
			
# Dans le script de ton personnage (Player.gd)
func die():
	ap.play("death")
	# 1. On joue le son de mort
	if not death_sound.playing:
		death_sound.play()
	
	# 2. On déclenche les particules
	ParticleManager.spawn("death", global_position)
	print("Le joueur est mort")
	
	# 3. Optionnel : On "fige" le joueur un court instant
	set_physics_process(false) # Empêche de bouger pendant le son
	
	# 4. On attend un peu (0.5s) pour entendre le son
	await get_tree().create_timer(0.5).timeout 
	
	# 5. On réapparaît (Remplace Vector2.ZERO par ta position de départ)
	respawn()
	
	spawn_sound.play()
	# 6. On redonne le contrôle
	set_physics_process(true)
	
func respawn():
	global_position = spawn_position
		
		
# Collision avec des pics
func _on_hurtbox_body_entered(_body: Node2D) -> void:
	die();
	
func getSpawnPoint():
	var spawn_points = get_tree().get_nodes_in_group("spawns")
	
	if spawn_points.size() > 0:
		var point = spawn_points[0]
		return point
	else:
		print("Erreur : Aucun SpawnPoint trouvé")
		return null


	
