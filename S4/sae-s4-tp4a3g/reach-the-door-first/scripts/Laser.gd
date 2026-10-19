extends GPUParticles2D



# Variable de mouvement
@export var is_moving = false
@export var distance = 10
@export var speed = 10

# Variable de clignotement
@export var clignotement = true
@export var flash_speed = 2

@onready var raycast = $RayCast2D
@onready var impact_particles = $ImpactParticles

var temps = 0.0
var position_depart = 0.0

const MAX_LENGTH = 1500

func _ready():
	position_depart = position.x

func _physics_process(_delta):
	if is_moving:
		move(_delta, distance)

	update()
			
func update():
	# 1. On projette le RayCast
	raycast.target_position = Vector2(0, MAX_LENGTH)
	raycast.force_raycast_update()
	
	var cast_point = raycast.target_position
	
	# 2. Si on touche un obstacle
	if raycast.is_colliding():
		var objet_touche = raycast.get_collider()
		
		
		# On vérifie si l'objet est un joueur ET s'il n'est pas déjà mort
		if objet_touche is CharacterBody2D and emitting:
			# On vérifie une variable 'is_dead' ou similaire sur le joueur
			# pour ne pas l'exécuter si le processus de mort est déjà lancé.
			if not objet_touche.get("is_dead"): 
				print("Un CharacterBody2D a touché un laser !")
				objet_touche.die()
		
			
		cast_point = to_local(raycast.get_collision_point())
		impact_particles.position = cast_point
		# L'émission des particules d'impact suit l'état du laser
		impact_particles.emitting = emitting 
	else:
		impact_particles.emitting = false
	
	
	
	if clignotement:
		clignoter()
		
	# 3. Calcul du Lifetime pour le faisceau de particules
	var mat = process_material as ParticleProcessMaterial
	if mat:
		var speed = mat.initial_velocity_min
		var distance = cast_point.length()
		
		# Formule brute : Temps = Distance / Vitesse
		if speed > 0:
			lifetime = distance / speed
		else:
			lifetime = 0.1

func move(delta, distance):
	temps += delta
	position.x = position_depart + sin(temps * speed) * distance

func clignoter():
	emitting = sin(Time.get_ticks_msec() / 1000.0 * flash_speed) > 0
	impact_particles.emitting = sin(Time.get_ticks_msec() / 1000.0 * flash_speed) > 0

	
