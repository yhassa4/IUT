extends CharacterBody2D

var is_dead = false

# --- Variables d'Équipe et de Contrôle ---
var current_controller_id : int = 0
var team_name : String = "" # Sera défini par le TeamManager au spawn

# --- Variables de mouvement ---
var move_direction = 0.0
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

# Gestion du spawn (via les groupes)
@onready var ap = $AnimatedSprite2D

# --- RÉFÉRENCES AUDIO ---
@onready var jump_sound = $JumpSound
@onready var death_sound = $DeathSound
@onready var spawn_sound = $SpawnSound

func _ready():
	add_to_group("players")
	# Au démarrage, on cherche le point de spawn
	moveToSpawnPoint()
	
	if spawn_sound:
		spawn_sound.play()

func _physics_process(delta):
	# BARRIÈRE : Si aucun contrôleur n'est assigné, le perso ne bouge pas du tout
	if current_controller_id == 0:
		return

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
		var jumped = false
		if coyote_timer > 0.0:
			velocity.y = JUMP_VELOCITY
			coyote_timer = 0.0 
			jumped = true
		elif is_on_wall() and not is_on_floor():
			var wall_normal = get_wall_normal()
			velocity.y = JUMP_VELOCITY
			velocity.x = wall_normal.x * WALL_JUMP_PUSH
			jumped = true
		
		if jumped and jump_sound:
			ParticleManager.spawn("jump", global_position)
			jump_sound.play(0.20)
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

# --- FONCTIONS DE CONTRÔLE AVEC SÉCURITÉ (SENDER_ID) ---

func jump(sender_id: int):
	if sender_id == current_controller_id:
		jump_just_pressed = true

func release_jump(sender_id: int):
	if sender_id == current_controller_id:
		jump_released = true

func set_move_direction(sender_id: int, dir: float):
	if sender_id == current_controller_id:
		move_direction = dir

# --- SYSTÈME DE MORT ET ROTATION ---

func die():
	
	if is_dead: return # Sécurité supplémentaire
	
	is_dead = true
	ap.play("death")
	if death_sound:
		death_sound.play()
		
	ParticleManager.spawn("death", global_position)
	print("Mort de l'équipe : ", team_name)
	
	# On stoppe le perso
	set_physics_process(false)
	
	# On demande au TeamManager de changer de joueur AVANT le respawn
	if TeamManager.has_method("rotate_player"):
		TeamManager.rotate_player(team_name)
		
	await get_tree().create_timer(0.5).timeout
	
	respawn()
	set_physics_process(true)

func respawn():
	moveToSpawnPoint()
	
	
	is_dead = false
	# On récupère l'index du chef actuel pour NOTRE équipe
	var index_chef = TeamManager.active_player_index[team_name]
	# On récupère l'ID correspondant dans la liste des joueurs de NOTRE équipe
	var id_du_nouveau_chef = TeamManager.teams[team_name][index_chef]
	# On assigne cet ID au personnage
	self.current_controller_id = id_du_nouveau_chef
	
	if spawn_sound:
		spawn_sound.play()

# Collision avec des pics
func _on_hurtbox_body_entered(_body: Node2D) -> void:
	die();

func getSpawnPoint():
	var points = get_tree().get_nodes_in_group("spawns")
	return points[0] if points.size() > 0 else null

func moveToSpawnPoint():
	var sp = getSpawnPoint()
	if sp:
		global_position = sp.global_position
		velocity = Vector2.ZERO # On reset la vitesse pour pas glisser au spawn
		
		# Dans ton script player.gd (attaché à res://scenes/player.tscn)

func apply_team_color(color_to_apply: Color):
	# On cherche le nœud "dot" (adapte le chemin si nécessaire)
	# Si "dot" est un Sprite2D ou un CanvasItem, on change sa propriété 'modulate'
	var dot_sprite = $dot 
	
	if dot_sprite:
		dot_sprite.modulate = color_to_apply
	else:
		push_error("Le nœud 'dot' est introuvable sur le joueur !")
