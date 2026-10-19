extends CharacterBody2D

# --- Variables de contrôle (Pilotées par le Serveur) ---
var move_direction = 0.0          # -1 (gauche), 1 (droite), 0 (stop)
var jump_just_pressed = false
var jump_released = false

# --- Paramètres de physique ---
const SPEED = 300.0
const ACCELERATION = 3000.0
const FRICTION = 4000.0
const JUMP_VELOCITY = -550.0
const MIN_JUMP_VELOCITY = -200.0
const WALL_JUMP_PUSH = 500.0
const WALL_SLIDE_SPEED = 150.0
const COYOTE_TIME = 0.15

var coyote_timer = 0.0
var gravity = ProjectSettings.get_setting("physics/2d/default_gravity")
var spawn_position = Vector2.ZERO

@onready var ap = $AnimatedSprite2D

# --- RÉFÉRENCES AUDIO ---
@onready var jump_sound = $JumpSound
@onready var death_sound = $DeathSound
@onready var spawn_sound = $SpawnSound

func _ready():
	spawn_position = global_position
	if spawn_sound:
		spawn_sound.play()

func _physics_process(delta):
	# 1. Gravité et Wall Slide
	if not is_on_floor():
		coyote_timer -= delta
		if is_on_wall() and velocity.y > 0 and move_direction != 0:
			velocity.y = min(velocity.y + gravity * delta, WALL_SLIDE_SPEED)
		else:
			velocity.y += gravity * delta
	else:
		coyote_timer = COYOTE_TIME

	# 2. Gestion du Saut
	if jump_just_pressed:
		var jumped = false # On vérifie si un saut a vraiment eu lieu
		
		if coyote_timer > 0.0:
			velocity.y = JUMP_VELOCITY
			coyote_timer = 0.0 
			jumped = true
		elif is_on_wall() and not is_on_floor():
			var wall_normal = get_wall_normal()
			velocity.y = JUMP_VELOCITY
			velocity.x = wall_normal.x * WALL_JUMP_PUSH
			jumped = true
		
		# --- AJOUT DU SON ICI ---
		if jumped and jump_sound:
			ParticleManager.spawn("jump", global_position)
			jump_sound.play(0.20)
			# On coupe le son après 0.4s pour éviter la boucle infinie de ton fichier
			get_tree().create_timer(0.4).timeout.connect(func(): jump_sound.stop())
		
		jump_just_pressed = false 
	
	# 3. Saut variable
	if jump_released:
		if velocity.y < MIN_JUMP_VELOCITY:
			velocity.y = MIN_JUMP_VELOCITY
		jump_released = false 

	# 4. Déplacement horizontal
	if move_direction != 0:
		velocity.x = move_toward(velocity.x, move_direction * SPEED, ACCELERATION * delta)
	else:
		velocity.x = move_toward(velocity.x, 0, FRICTION * delta)

	# 5. Mise à jour de l'animation et mouvement
	update_animation(move_direction)
	move_and_slide()

func update_animation(direction):
	if direction != 0:
		ap.flip_h = direction < 0

	if not is_on_floor():
		ap.play("jump")
	elif direction != 0:
		ap.play("walk")
	else:
		ap.play("idle")

# --- FONCTIONS APPELÉES PAR LE SERVEUR ---

func jump():
	jump_just_pressed = true

func release_jump():
	jump_released = true

func die():
	if death_sound:
		death_sound.play()
		
	ParticleManager.spawn("death", global_position)
	print("Mort du joueur : ", name)
	
	set_physics_process(false)
	await get_tree().create_timer(0.5).timeout
	
	global_position = spawn_position
	velocity = Vector2.ZERO
	
	if spawn_sound:
		spawn_sound.play()
		
	set_physics_process(true)
